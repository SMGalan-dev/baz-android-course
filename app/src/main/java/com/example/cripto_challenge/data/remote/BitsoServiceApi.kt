package com.example.cripto_challenge.data.remote

import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerBaseResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BitsoServiceApi {

    @GET("available_books")
    suspend fun getAvailableBooks(): Response<AvailableBooksBaseResponse>

    @GET("order_book/")
    suspend fun getOrderBook(
        @Query("book") book: String
    ): Response<OrderBookBaseResponse>

    @GET("ticker/")
    suspend fun getTicker(
        @Query("book") book: String,
    ): Response<TickerBaseResponse>

    @GET("available_books")
    fun getAvailableBooksRxJava(): Observable<Response<AvailableBooksBaseResponse>>
}
