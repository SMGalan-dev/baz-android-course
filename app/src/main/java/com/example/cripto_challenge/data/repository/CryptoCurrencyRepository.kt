package com.example.cripto_challenge.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker

interface CryptoCurrencyRepository {
    suspend fun getAvailableBooks(): List<AvailableOrderBook>?
    suspend fun getTicker(book: String): Ticker?
    suspend fun getOrderBook(book: String): OrderBook?
    fun getAvailableBooksRxJava(): MutableLiveData<List<AvailableOrderBook>>
}