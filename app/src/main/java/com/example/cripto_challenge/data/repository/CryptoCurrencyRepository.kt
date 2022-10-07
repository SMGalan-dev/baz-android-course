package com.example.cripto_challenge.data.repository

import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface CryptoCurrencyRepository {
    fun getAvailableBooks(): Flow<RequestState<List<AvailableOrderBook>>>
    fun getTicker(book: String): Flow<RequestState<Ticker>>
    fun getOrderBook(book: String): Flow<RequestState<OrderBook>>
    fun getAvailableBooksRxJava(): Single<AvailableBooksBaseResponse>
    fun updateAvailableOrderBookDatabase(bookList: List<AvailableOrderBook>)
}
