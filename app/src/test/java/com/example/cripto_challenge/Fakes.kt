package com.example.cripto_challenge

import com.example.cripto_challenge.data.database.entities.toAvailableOrderBookEntityList
import com.example.cripto_challenge.data.remote.dto.base.AvailableOrderBookResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.model.OpenOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

val listOneItemResponse = listOf(
    AvailableOrderBookResponse(
        "btc_mxn",
        "0.00000030000",
        "3000",
        "40000",
        "20000000",
        "10.00",
        "200000000"
    )
)

val availableOrderBookObject = AvailableOrderBook(
    book_code = "btc_mxn",
    book_name = "Bitcoin",
    book_format_code = "BTC/MXN",
    book_logo = R.drawable.ic_bitcoin_logo
)

val listAvailableOrderBooks = listOf(availableOrderBookObject, availableOrderBookObject)

val listAvailableOrderBooksEntities = listOf(availableOrderBookObject, availableOrderBookObject).toAvailableOrderBookEntityList()

val openOrder = OpenOrder("btc_mxn", "1.00", "2.00")