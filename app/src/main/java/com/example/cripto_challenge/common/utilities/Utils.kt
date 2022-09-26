package com.example.cripto_challenge.common.utilities

import com.example.cripto_challenge.data.remote.dto.base.AvailableOrderBookResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import java.text.NumberFormat

fun List<AvailableOrderBookResponse>?.toMXNAvailableOrderBookList() = mutableListOf<AvailableOrderBook>()
    .apply {
        this@toMXNAvailableOrderBookList?.forEach {
            if (it.book?.contains("mxn") == true) this.add(it.toMXNAvailableOrderBook())
        }
    }

fun String?.toBookName(): String  = when(this) {
        BookType.BITCOIN.value -> "Bitcoin"
        BookType.ETHEREUM.value -> "Ethereum"
        BookType.XRP.value -> "XRP"
        BookType.LITECOIN.value -> "Litecoin"
        BookType.BITCOIN_CASH.value -> "Bitcoin Cash"
        BookType.TRUEUSD.value -> "True USD"
        BookType.DECETRALAND.value -> "Decentraland"
        BookType.BASIC_ATENTION_TOKEN.value -> "Basic Attention Token"
        BookType.DAI.value -> "Dai"
        BookType.USD_COIN.value -> "USD coin"
        else -> ""
    }

fun String?.toBookCodeFormat(): String  =
    this?.replace("_", "/")?.uppercase() ?: ""

fun Double.formatAsCurrency(): String = NumberFormat.getCurrencyInstance().format(this)