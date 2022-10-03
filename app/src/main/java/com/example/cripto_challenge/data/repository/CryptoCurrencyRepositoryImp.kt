package com.example.cripto_challenge.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cripto_challenge.R
import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.common.utilities.isInternetAvailable
import com.example.cripto_challenge.common.utilities.toMXNAvailableOrderBookList
import com.example.cripto_challenge.data.database.data_source.CryptoCurrencyLocalDataSource
import com.example.cripto_challenge.data.database.entities.*
import com.example.cripto_challenge.data.remote.data_source.CryptoCurrencyNetworkDataSource
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CryptoCurrencyRepositoryImp @Inject constructor(
    @ApplicationContext private val context: Context,
    private val remoteDataSource: CryptoCurrencyNetworkDataSource,
    private val localDataSource: CryptoCurrencyLocalDataSource,
): CryptoCurrencyRepository{

    override fun getAvailableBooks(): Flow<RequestState<List<AvailableOrderBook>>> = flow {
        emit(RequestState.Loading())
        if (isInternetAvailable(context)) {
            try {
                val response = remoteDataSource.getAvailableBooks().let {
                    it.body()?.availableBooksListData.toMXNAvailableOrderBookList()
                }
                updateAvailableOrderBookDatabase(response)
                emit(RequestState.Success(response))
            } catch (e: HttpException) {
                emit(RequestState.Error(e.localizedMessage ?: context.getString(R.string.http_unexpected_error)))
            } catch (e: IOException) {
                emit(RequestState.Error(context.getString(R.string.interrupted_internet_error)))
            }
        } else localDataSource.getAllAvailableOrderBookFromDatabase().run {
            if (this.isNullOrEmpty()) emit(RequestState.Error(context.getString(R.string.no_data_internet_error)))
            else emit(RequestState.Error(context.getString(R.string.data_internet_error), this.toAvailableOrderBookListFromEntity()))
        }
    }

    override fun getTicker(book: String): Flow<RequestState<Ticker>> = flow {
        emit(RequestState.Loading())
        if (isInternetAvailable(context)){
            try {
                val response = remoteDataSource.getTicker(book = book).let {
                    it.body()?.tickerData?.toTicker()
                } ?: run { Ticker()}
                updateTickerDatabase(book, response)
                emit(RequestState.Success(response))
            } catch (e: HttpException) {
                emit(RequestState.Error(context.getString(R.string.ticker_error) + e.localizedMessage))
            } catch (e: IOException) {
                emit(RequestState.Error(context.getString(R.string.ticker_error) + context.getString(R.string.interrupted_internet_error)))
            }
        }else localDataSource.getTickerFromDatabase(book).toTickerFromEntity().run {
            if (this.book.isNullOrEmpty()) emit(RequestState.Error(context.getString(R.string.ticker_error) + context.getString(R.string.no_data_internet_error)))
            else emit(RequestState.Error(context.getString(R.string.ticker_error) + context.getString(R.string.data_internet_error), this))
        }
    }

    override fun getOrderBook(book: String): Flow<RequestState<OrderBook>> = flow {
        if (isInternetAvailable(context)){
            try {
                emit(RequestState.Loading())
                val response = remoteDataSource.getOrderBook(book).let {
                    it.body()?.orderBookData?.toOrderBook(book)
                } ?: OrderBook()
                updateOrderBookDatabase(book, response)
                emit(RequestState.Success(response))
            } catch (e: HttpException) {
                emit(RequestState.Error(context.getString(R.string.order_book_error) + e.localizedMessage))
            } catch (e: IOException) {
                emit(RequestState.Error(context.getString(R.string.order_book_error) + context.getString(R.string.interrupted_internet_error)))
            }
        } else localDataSource.getOrderBookfromDatabase(book).run {
            if (this.asks.isNullOrEmpty()) emit(RequestState.Error(context.getString(R.string.order_book_error) + context.getString(R.string.no_data_internet_error)))
            else emit(RequestState.Error(context.getString(R.string.order_book_error) + context.getString(R.string.data_internet_error), this))
        }
    }

    override fun getAvailableBooksRxJava(): MutableLiveData<List<AvailableOrderBook>> = MutableLiveData<List<AvailableOrderBook>>().apply{
        if (isInternetAvailable(context)){
            CompositeDisposable().add(remoteDataSource.getAvailableBooksRxJava()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it != null && it.isSuccessful){
                        with(it.body()?.availableBooksListData.toMXNAvailableOrderBookList()){
                            this@apply.postValue(this)
                            updateAvailableOrderBookDatabase(this)
                        }
                    }
                }
            )
        } else localDataSource.getAllAvailableOrderBookFromDatabase().let {
            this@apply.postValue(it.toAvailableOrderBookListFromEntity())
        }
    }

    private fun updateAvailableOrderBookDatabase(bookList: List<AvailableOrderBook>){
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.getAllAvailableOrderBookFromDatabase().run {
                if (this.isNullOrEmpty()) {
                    localDataSource.insertAvailableOrderBookToDatabase(bookList.toAvailableOrderBookEntityList())
                    Log.i("CriptoCurrencyDataBase", "AvailableOrderBookEntity inserted")
                } else {
                    localDataSource.updateAvailableOrderBookDatabase(bookList.toAvailableOrderBookEntityList())
                    Log.i("CriptoCurrencyDataBase", "AvailableOrderBookEntity updated")
                }
            }
        }
    }

    private fun updateTickerDatabase(book: String, ticker: Ticker) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.deleteTickerDatabase(book)
            localDataSource.insertTickerToDatabase(ticker.toTickerEntity())
            Log.i("CriptoCurrencyDataBase", "Ticker inserted")
        }
    }

    private fun updateOrderBookDatabase(book: String, orderBook: OrderBook?) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.deleteOpenOrdersFromDatabase(book)
            localDataSource.insertOpenOrdersToDatabase(orderBook?.bids.toBidsEntityList(), orderBook?.asks.toAsksEntityList())
            Log.i("CriptoCurrencyDataBase", "OrderBook inserted")
        }
    }

}