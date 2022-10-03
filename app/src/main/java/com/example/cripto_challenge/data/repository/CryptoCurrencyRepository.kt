package com.example.cripto_challenge.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface CryptoCurrencyRepository {
    fun getAvailableBooks(): Flow<RequestState<List<AvailableOrderBook>>>
    suspend fun getTicker(book: String): Ticker?
    suspend fun getOrderBook(book: String): OrderBook?
    fun getAvailableBooksRxJava(): MutableLiveData<List<AvailableOrderBook>>
}