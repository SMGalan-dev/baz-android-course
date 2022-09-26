package com.example.cripto_challenge.data.remote

import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.OrderBookBaseResponse
import com.example.cripto_challenge.data.remote.dto.response.TickerBaseResponse
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
    suspend fun getAvaliableBooks(): Response<AvailableBooksBaseResponse>

    @GET("order_book/")
    suspend fun getOrderBook(
        @Query("book") book: String
    ): Response<OrderBookBaseResponse>

    @GET("ticker/")
    suspend fun getTicker(
        @Query("book") book: String,
    ): Response<TickerBaseResponse>

    //Test
    @GET("ticker/")
    suspend fun getTickerDirectly(
        @Query("book") book: String,
    ): Any
}