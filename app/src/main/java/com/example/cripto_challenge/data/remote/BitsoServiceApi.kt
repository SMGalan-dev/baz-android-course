package com.example.cripto_challenge.data.remote

import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BitsoServiceApi {

    /*
    class Builder(context: Context) : APIServiceAutoBuilder<BitsoServiceApi>(context) {
        fun build(): BitsoServiceApi {
            return  getRetrofit().create(BitsoServiceApi::class.java)
        }
    }
     */

    @GET("available_books")
    suspend fun getAvaliableBooks(): Response<AvailableBooksResponse>

    @GET("order_book/")
    suspend fun getOrderBook(
        @Query("book") book: String
    ): Response<OrderBookResponse>

    @GET("ticker/")
    suspend fun getTicker(
        @Query("book") book: String,
    ): Response<TickerResponse>

    //Test
    @GET("ticker/")
    suspend fun getTickerDirectly(
        @Query("book") book: String,
    ): Any
}