package com.example.cripto_challenge.domain.use_case

import android.util.Log
import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.common.utilities.toAvailableOrderBookEntityList
import com.example.cripto_challenge.common.utilities.toAvailableOrderBookListFromEntity
import com.example.cripto_challenge.common.utilities.toMXNAvailableOrderBookList
import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity
import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerBaseResponse
import com.example.cripto_challenge.data.repository.CryptoCurrencyRepository
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CurrencyUseCase (private val repository: CryptoCurrencyRepository) {

    fun getAvailableBooks(): Flow<RequestState<List<AvailableOrderBook>>> = flow {
        try {
            emit(RequestState.Loading())
            val availableBooks: List<AvailableOrderBook> = repository.getAvailableBooks().let {
                (it.body() as AvailableBooksBaseResponse).availableBooksListData.toMXNAvailableOrderBookList()
            }
            updateAvailableBooksDB(availableBooks.toAvailableOrderBookEntityList())
            emit(RequestState.Success(availableBooks))
        } catch (e: HttpException) {
            emit(RequestState.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            val dataFromDB = repository.getAllCryptoCurrencyFromDatabase().let { it.toAvailableOrderBookListFromEntity()}
            if (dataFromDB.isNullOrEmpty()) emit(RequestState.Error("Couldn't reach server. Check your internet connection. \nNo stored data"))
            else emit(RequestState.Error("Couldn't reach server. Check your internet connection. \nShowing stored data", dataFromDB))
        }
    }

    fun getTicker(book: String): Flow<RequestState<Ticker>> = flow {
        try {
            emit(RequestState.Loading())
            val response = repository.getTicker(book)
            if (response.body()?.success == true ){
                val ticker: Ticker = response.let {
                    it.body()?.tickerData?.toTicker() ?: Ticker()
                }
                emit(RequestState.Success(ticker))
            } else {
                val errorBody = Gson().fromJson(response.errorBody()?.string(), TickerBaseResponse::class.java) ?: TickerBaseResponse()
                emit(RequestState.Error(errorBody.error?.message ?: ""))
            }
        } catch (e: HttpException) {
            emit(RequestState.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(RequestState.Error("Couldn't reach server."))
        }
    }

    fun getOrderBook(book: String): Flow<RequestState<OrderBook>> = flow {
        try {
            emit(RequestState.Loading())
            val response = repository.getOrderBook(book)
            if (response.body()?.success == true ){
                val orderBook: OrderBook = response.let {
                    it.body()?.orderBookData?.toOrderBook() ?: OrderBook()
                }
                emit(RequestState.Success(orderBook))
            } else {
                val errorBody = Gson().fromJson(response.errorBody()?.string(), OrderBookBaseResponse::class.java) ?: OrderBookBaseResponse()
                emit(RequestState.Error(errorBody.error?.message ?: ""))
            }
        } catch (e: HttpException) {
            emit(RequestState.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(RequestState.Error("Couldn't reach server. Check your internet connection."))
        }
    }


    fun updateAvailableBooksDB(bookList: List<AvailableOrderBookEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAllCryptoCurrencyFromDatabase().run {
                if (this.isNullOrEmpty()){
                    Log.i("CriptoCurrencyDataBase", "AvailableOrderBookEntity inserted")
                    repository.insertCryptoCurrencyToDatabase(bookList)
                } else{
                    Log.i("CriptoCurrencyDataBase", "AvailableOrderBookEntity updated")
                    repository.updateCryptoCurrencyToDatabase(bookList)
                }
            }
        }
    }

}