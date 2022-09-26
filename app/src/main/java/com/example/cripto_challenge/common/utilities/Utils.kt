package com.example.cripto_challenge.common.utilities

import com.example.cripto_challenge.R
import com.example.cripto_challenge.data.remote.dto.base.AvailableOrderBookResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import java.text.NumberFormat

fun List<AvailableOrderBookResponse>?.toMXNAvailableOrderBookList() = mutableListOf<AvailableOrderBook>()
    .apply {
        this@toMXNAvailableOrderBookList?.forEach {
            if (it.book?.contains("mxn") == true) this.add(it.toMXNAvailableOrderBook())
        }
    }


fun setLogo(book: String): Int {
    val icon = when(book) {
        BookType.BITCOIN.value -> R.drawable.ic_bitcoin_logo
        BookType.ETHEREUM.value -> R.drawable.ic_ethereum_logo
        BookType.XRP.value -> R.drawable.ic_xrp_logo
        BookType.LITECOIN.value -> R.drawable.ic_litecoin_logo
        BookType.BITCOIN_CASH.value -> R.drawable.ic_bitcoin_cash_logo
        BookType.TRUEUSD.value -> R.drawable.ic_trueusd_logo
        BookType.DECETRALAND.value -> R.drawable.ic_decentraland_logo
        BookType.BASIC_ATENTION_TOKEN.value -> R.drawable.ic_bat_logo
        BookType.DAI.value -> R.drawable.ic_dai_logo
        BookType.USD_COIN.value -> R.drawable.ic_usd_coin_logo
        else -> android.R.drawable.ic_dialog_info
    }
    return icon
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