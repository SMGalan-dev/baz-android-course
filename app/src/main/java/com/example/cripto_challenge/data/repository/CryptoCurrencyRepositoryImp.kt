package com.example.cripto_challenge.data.repository

import com.example.cripto_challenge.common.utilities.toAvailableOrderBookListFromEntity
import com.example.cripto_challenge.common.utilities.toMXNAvailableOrderBookList
import com.example.cripto_challenge.data.database.data_source.CryptoCurrencyLocalDataSource
import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity
import com.example.cripto_challenge.data.remote.data_source.CryptoCurrencyNetworkDataSource
import com.example.cripto_challenge.data.remote.dto.response.OrderBookBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerBaseResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import retrofit2.Response

class CryptoCurrencyRepositoryImp(
    private val remoteDataSource: CryptoCurrencyNetworkDataSource,
    private val localDataSource: CryptoCurrencyLocalDataSource,
): CryptoCurrencyRepository{

    override suspend fun getAvailableBooks(): List<AvailableOrderBook> =
        remoteDataSource.getAvailableBooks().let {
            it.body()?.availableBooksListData.toMXNAvailableOrderBookList()
        }

    override suspend fun getOrderBook(book: String): Response<OrderBookBaseResponse> =
        remoteDataSource.getOrderBook(book = book)

    override suspend fun getTicker(book: String): Response<TickerBaseResponse> =
        remoteDataSource.getTicker(book = book)

    override suspend fun getAllCryptoCurrencyFromDatabase(): List<AvailableOrderBook>? =
        localDataSource.getAllCryptoCurrencyFromDatabase().let {
            if (it.isNotEmpty()) it.toAvailableOrderBookListFromEntity()
            else null
        }

    override suspend fun insertCryptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>) =
        localDataSource.insertCryptoCurrencyToDatabase(bookList)

    override suspend fun updateCryptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>) =
        localDataSource.updateCryptoCurrencyToDatabase(bookList)

}