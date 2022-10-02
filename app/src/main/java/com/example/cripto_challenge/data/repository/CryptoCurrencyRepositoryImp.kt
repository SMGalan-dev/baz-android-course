package com.example.cripto_challenge.data.repository

import android.content.Context
import android.util.Log
import com.example.cripto_challenge.common.utilities.isInternetAvailable
import com.example.cripto_challenge.common.utilities.toAvailableOrderBookEntityList
import com.example.cripto_challenge.common.utilities.toAvailableOrderBookListFromEntity
import com.example.cripto_challenge.common.utilities.toMXNAvailableOrderBookList
import com.example.cripto_challenge.data.database.data_source.CryptoCurrencyLocalDataSource
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
                    localDataSource.getAllCryptoCurrencyFromDatabase().run {
                        if (this.isNullOrEmpty()) {
                            Log.i("CriptoCurrencyDataBase", "AvailableOrderBookEntity inserted")
                            localDataSource.insertCryptoCurrencyToDatabase(bookList.toAvailableOrderBookEntityList())
                        } else {
                            Log.i("CriptoCurrencyDataBase", "AvailableOrderBookEntity updated")
                            localDataSource.updateCryptoCurrencyToDatabase(bookList.toAvailableOrderBookEntityList())
                        }
                    }
                }
            }
        } else localDataSource.getAllCryptoCurrencyFromDatabase().let {
            if (it.isNotEmpty()) it.toAvailableOrderBookListFromEntity()
            else null
        }

    override suspend fun getTicker(book: String): Ticker? =
        if (isInternetAvailable(context)){
            remoteDataSource.getTicker(book = book).let {
                it.body()?.tickerData?.toTicker()
            }.also {
                Log.i("CriptoCurrencyDataBase", "Ticker inserted")
            }
        } else null

    override suspend fun getOrderBook(book: String): OrderBook? =
        if (isInternetAvailable(context)){
            remoteDataSource.getOrderBook(book = book).let {
                it.body()?.orderBookData?.toOrderBook()
            }.also {
                Log.i("CriptoCurrencyDataBase", "OrderBook inserted")
            }
        } else null


}