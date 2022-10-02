package com.example.cripto_challenge.data.repository

import android.content.Context
import android.util.Log
import com.example.cripto_challenge.common.utilities.isInternetAvailable
import com.example.cripto_challenge.common.utilities.toMXNAvailableOrderBookList
import com.example.cripto_challenge.data.database.data_source.CryptoCurrencyLocalDataSource
import com.example.cripto_challenge.data.database.entities.*
import com.example.cripto_challenge.data.remote.data_source.CryptoCurrencyNetworkDataSource
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoCurrencyRepositoryImp @Inject constructor(
    @ApplicationContext private val context: Context,
    private val remoteDataSource: CryptoCurrencyNetworkDataSource,
    private val localDataSource: CryptoCurrencyLocalDataSource,
): CryptoCurrencyRepository{

    override suspend fun getAvailableBooks(): List<AvailableOrderBook>? =
        if (isInternetAvailable(context)) {
            remoteDataSource.getAvailableBooks().let {
                it.body()?.availableBooksListData.toMXNAvailableOrderBookList()
            }.also { bookList ->
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
        } else localDataSource.getAllAvailableOrderBookFromDatabase().let {
            if (it.isNotEmpty()) it.toAvailableOrderBookListFromEntity()
            else null
        }

    override suspend fun getTicker(book: String): Ticker? =
        if (isInternetAvailable(context)){
            remoteDataSource.getTicker(book = book).let {
                it.body()?.tickerData?.toTicker()
            }?.also { ticker ->
                CoroutineScope(Dispatchers.IO).launch {
                    localDataSource.deleteTickerDatabase(book)
                    localDataSource.insertTickerToDatabase(ticker.toTickerEntity())
                    Log.i("CriptoCurrencyDataBase", "Ticker inserted")
                }
            }
        } else localDataSource.getTickerFromDatabase(book).toTickerFromEntity().let {
            if (it.book.isNullOrEmpty()) null
            else it
        }

    override suspend fun getOrderBook(book: String): OrderBook? =
        if (isInternetAvailable(context)){
            remoteDataSource.getOrderBook(book = book).let {
                it.body()?.orderBookData?.toOrderBook(book)
            }.also {orderBook ->
                CoroutineScope(Dispatchers.IO).launch {
                    localDataSource.deleteOpenOrdersFromDatabase(book)
                    localDataSource.insertOpenOrdersToDatabase(orderBook?.bids.toBidsEntityList(), orderBook?.asks.toAsksEntityList())
                    Log.i("CriptoCurrencyDataBase", "OrderBook inserted")
                }
            }
        } else localDataSource.getOrderBookfromDatabase(book).let {
            if (it.book.isNullOrEmpty()) null
            else it
        }

}