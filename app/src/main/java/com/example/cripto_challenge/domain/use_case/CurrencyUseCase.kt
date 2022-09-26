package com.example.cripto_challenge.domain.use_case

import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.common.utilities.toMXNAvailableOrderBookList
import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerBaseResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker
import com.example.cripto_challenge.domain.repository.BitsoServiceRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

//Here goes all the business logic
class CurrencyUseCase (private val repository: BitsoServiceRepository) {

    //TODO this is a demo for the class
    //suspend fun getAvailableBooks(): AvailableBooksResponse = repository.loadCharacters()
    //suspend operator fun invoke(): AvailableBooksResponse = repository.loadCharacters() //for one function only

    fun getAvailableBooks(): Flow<RequestState<List<AvailableOrderBook>>> = flow {
        try {
            emit(RequestState.Loading<List<AvailableOrderBook>>())
            val availableBooks: List<AvailableOrderBook> = repository.getAvaliableBooks().let {
                (it.body() as AvailableBooksBaseResponse).availableBooksListData.toMXNAvailableOrderBookList()
            }
            emit(RequestState.Success(availableBooks))
        } catch (e: HttpException) {
            emit(RequestState.Error<List<AvailableOrderBook>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(RequestState.Error<List<AvailableOrderBook>>("Couldn't reach server. Check your internet connection."))
        }
    }

    fun getTicker(book: String): Flow<RequestState<Ticker>> = flow {
        try {
            emit(RequestState.Loading<Ticker>())
            val response = repository.getTicker(book)
            if (response.body()?.success == true ){
                val ticker: Ticker = response.let {
                    it.body()?.tickerData?.toTicker() ?: Ticker()
                }
                emit(RequestState.Success(ticker))
            } else {
                val errorBody = Gson().fromJson(response.errorBody()?.string(), TickerBaseResponse::class.java) ?: TickerBaseResponse()
                emit(RequestState.Error<Ticker>(errorBody.error?.message ?: ""))
            }
        } catch (e: HttpException) {
            emit(RequestState.Error<Ticker>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(RequestState.Error<Ticker>("Couldn't reach server. Check your internet connection."))
        }
    }

    fun getOrderBook(book: String): Flow<RequestState<OrderBook>> = flow {
        try {
            emit(RequestState.Loading<OrderBook>())
            val response = repository.getOrderBook(book)
            if (response.body()?.success == true ){
                val orderBook: OrderBook = response.let {
                    it.body()?.orderBookData?.toOrderBook() ?: OrderBook()
                }
                emit(RequestState.Success(orderBook))
            } else {
                val errorBody = Gson().fromJson(response.errorBody()?.string(), OrderBookBaseResponse::class.java) ?: OrderBookBaseResponse()
                emit(RequestState.Error<OrderBook>(errorBody.error?.message ?: ""))
            }
        } catch (e: HttpException) {
            emit(RequestState.Error<OrderBook>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(RequestState.Error<OrderBook>("Couldn't reach server. Check your internet connection."))
        }
    }
}