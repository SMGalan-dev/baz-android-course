package com.example.cripto_challenge.data.repository

import com.example.cripto_challenge.common.RetrofitClient
import com.example.cripto_challenge.data.remote.BitsoServiceApi
import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerResponse
import com.example.cripto_challenge.domain.repository.BitsoServiceRepository
import retrofit2.Response

//import javax.inject.Inject

class BitsoServiceRepositoryImp (private val api: BitsoServiceApi)  : BitsoServiceRepository {

    private val apiVal = RetrofitClient.repository()

    override suspend fun getAvaliableBooks(): Response<AvailableBooksResponse> =
        api.getAvaliableBooks()

    override suspend fun getOrderBook(book: String): Response<OrderBookResponse> =
        api.getOrderBook(book = book)

    override suspend fun getTicker(book: String): Response<TickerResponse> =
        api.getTicker(book = book)

    override suspend fun getTickerDirectly(book: String): Any =
        apiVal.getTickerDirectly(book = book)

}