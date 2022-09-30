package com.example.cripto_challenge.data.repository

import com.example.cripto_challenge.data.database.data_source.CryptoCurrencyLocalDataSource
import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity
import com.example.cripto_challenge.data.remote.data_source.CryptoCurrencyNetworkDataSource
import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerBaseResponse
import retrofit2.Response

class CryptoCurrencyRepositoryImp(
    private val networkDataSource: CryptoCurrencyNetworkDataSource,
    private val localDataSource: CryptoCurrencyLocalDataSource,
): CryptoCurrencyRepository{

    override suspend fun getAvailableBooks(): Response<AvailableBooksBaseResponse> =
        networkDataSource.getAvailableBooks()

    override suspend fun getOrderBook(book: String): Response<OrderBookBaseResponse> =
        networkDataSource.getOrderBook(book = book)

    override suspend fun getTicker(book: String): Response<TickerBaseResponse> =
        networkDataSource.getTicker(book = book)

    override suspend fun getAllCryptoCurrencyFromDatabase(): List<AvailableOrderBookEntity> =
        localDataSource.getAllCryptoCurrencyFromDatabase()

    override suspend fun insertCryptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>) =
        localDataSource.insertCryptoCurrencyToDatabase(bookList)

    override suspend fun updateCryptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>) =
        localDataSource.updateCryptoCurrencyToDatabase(bookList)

}