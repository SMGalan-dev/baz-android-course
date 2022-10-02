package com.example.cripto_challenge.data.repository

import com.example.cripto_challenge.data.remote.dto.response.OrderBookBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerBaseResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import retrofit2.Response

interface CryptoCurrencyRepository {
    suspend fun getAvailableBooks(): List<AvailableOrderBook>?
    suspend fun getOrderBook(book: String): Response<OrderBookBaseResponse>
    suspend fun getTicker(book: String): Response<TickerBaseResponse>
}