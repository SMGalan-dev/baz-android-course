package com.example.cripto_challenge.data.remote.data_source

import com.example.cripto_challenge.data.remote.BitsoServiceApi
import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerBaseResponse
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class CryptoCurrencyNetworkDataSource @Inject constructor(
    private val api: BitsoServiceApi
) {
    suspend fun getAvailableBooks(): Response<AvailableBooksBaseResponse> =
        api.getAvailableBooks()

    suspend fun getOrderBook(book: String): Response<OrderBookBaseResponse> =
        api.getOrderBook(book = book)

    suspend fun getTicker(book: String): Response<TickerBaseResponse> =
        api.getTicker(book = book)

    fun getAvailableBooksRxJava(): Observable<Response<AvailableBooksBaseResponse>> =
        api.getAvailableBooksRxJava()
}
