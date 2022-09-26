package com.example.cripto_challenge.domain.repository

import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerBaseResponse
import retrofit2.Response

interface BitsoServiceRepository {
    suspend fun getAvaliableBooks(): Response<AvailableBooksBaseResponse>
    suspend fun getOrderBook(book: String): Response<OrderBookBaseResponse>
    suspend fun getTicker(book: String): Response<TickerBaseResponse>
}