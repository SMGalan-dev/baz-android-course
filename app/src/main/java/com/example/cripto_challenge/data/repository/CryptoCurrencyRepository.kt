package com.example.cripto_challenge.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface CryptoCurrencyRepository {
    fun getAvailableBooks(): Flow<RequestState<List<AvailableOrderBook>>>
    fun getTicker(book: String): Flow<RequestState<Ticker>>
    fun getOrderBook(book: String): Flow<RequestState<OrderBook>>
    fun getAvailableBooksRxJava(): MutableLiveData<List<AvailableOrderBook>>
}
