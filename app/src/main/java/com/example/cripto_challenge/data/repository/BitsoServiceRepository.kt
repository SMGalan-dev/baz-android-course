package com.example.cripto_challenge.domain.repository

import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerResponse
import retrofit2.Response

interface BitsoServiceRepository {
    suspend fun getAvaliableBooks(): Response<AvailableBooksResponse>
    suspend fun getOrderBook(book: String): Response<OrderBookResponse>
    suspend fun getTicker(book: String): Response<TickerResponse>
    suspend fun getTickerDirectly(book: String): Any
}