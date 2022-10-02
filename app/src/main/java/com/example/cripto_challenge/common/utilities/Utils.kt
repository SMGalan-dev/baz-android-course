package com.example.cripto_challenge.common.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }

    return result
}