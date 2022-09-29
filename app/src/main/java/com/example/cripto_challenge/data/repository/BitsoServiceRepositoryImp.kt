package com.example.cripto_challenge.data.repository

import com.example.cripto_challenge.data.database.dao.CriptoCurrencyDao
import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity
import com.example.cripto_challenge.data.remote.BitsoServiceApi
import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerBaseResponse
import retrofit2.Response

class BitsoServiceRepositoryImp (
    private val api: BitsoServiceApi,
    private val localDB: CriptoCurrencyDao
    ): BitsoServiceRepository{

    override suspend fun getAvaliableBooks(): Response<AvailableBooksBaseResponse> =
        api.getAvaliableBooks()

    override suspend fun getOrderBook(book: String): Response<OrderBookBaseResponse> =
        api.getOrderBook(book = book)

    override suspend fun getTicker(book: String): Response<TickerBaseResponse> =
        api.getTicker(book = book)

    override suspend fun getAllCriptoCurrencyFromDatabase(): List<AvailableOrderBookEntity> =
        localDB.getAllCriptoCurrencyFromDatabase()

    override suspend fun insertCriptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>) =
        localDB.insertCriptoCurrencyToDatabase(bookList)

    override suspend fun updateCriptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>) =
        localDB.updateCriptoCurrencyToDatabase(bookList)

}