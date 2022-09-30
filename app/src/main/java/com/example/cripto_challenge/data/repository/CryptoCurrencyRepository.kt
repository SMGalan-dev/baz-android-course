package com.example.cripto_challenge.data.repository

import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity
import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerBaseResponse
import retrofit2.Response

interface CryptoCurrencyRepository {
    suspend fun getAvailableBooks(): Response<AvailableBooksBaseResponse>
    suspend fun getOrderBook(book: String): Response<OrderBookBaseResponse>
    suspend fun getTicker(book: String): Response<TickerBaseResponse>
    suspend fun insertCryptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>)
    suspend fun getAllCryptoCurrencyFromDatabase(): List<AvailableOrderBookEntity>
    suspend fun updateCryptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>)

}