package com.example.cripto_challenge.common.utilities

import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity
import com.example.cripto_challenge.data.remote.dto.base.AvailableOrderBookResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import java.text.NumberFormat


fun String?.toBookCodeFormat(): String  =
    this?.replace("_", "/")?.uppercase() ?: ""

fun Double.formatAsCurrency(): String = NumberFormat.getCurrencyInstance().format(this)

fun List<AvailableOrderBookResponse>?.toMXNAvailableOrderBookList() = mutableListOf<AvailableOrderBook>()
    .apply {
        this@toMXNAvailableOrderBookList?.forEach {
            if (it.book?.contains("mxn") == true) this.add(it.toMXNAvailableOrderBook())
        }
    }

fun List<AvailableOrderBookEntity>?.toAvailableOrderBookListFromEntity() = mutableListOf<AvailableOrderBook>()
    .apply {
        this@toAvailableOrderBookListFromEntity?.forEach {
            this.add(
                AvailableOrderBook(
                    book_code = it.book_code,
                    book_name = it.book_name,
                    book_format_code = it.book_format_code,
                    book_logo = it.book_logo
                )
            )
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

fun List<AvailableOrderBook>?.toAvailableOrderBookEntityList() = mutableListOf<AvailableOrderBookEntity>()
    .apply {
        this@toAvailableOrderBookEntityList?.forEach {
            this.add(
                AvailableOrderBookEntity(
                    book_code = it.book_code,
                    book_name = it.book_name,
                    book_format_code = it.book_format_code,
                    book_logo = it.book_logo
                )
            )
        }
    }