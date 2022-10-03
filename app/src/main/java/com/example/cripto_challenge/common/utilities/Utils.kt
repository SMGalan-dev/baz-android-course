package com.example.cripto_challenge.common.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.cripto_challenge.common.Constants
import com.example.cripto_challenge.common.Constants.DIAGONAL_VALUE
import com.example.cripto_challenge.common.Constants.UNDERSCORE_VALUE
import com.example.cripto_challenge.data.remote.dto.base.AvailableOrderBookResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import java.text.NumberFormat


fun String?.toBookCodeFormat(): String  =
    this?.replace(UNDERSCORE_VALUE, DIAGONAL_VALUE)?.uppercase().orEmpty()

fun Double.formatAsCurrency(): String = NumberFormat.getCurrencyInstance().format(this)

fun List<AvailableOrderBookResponse>?.toMXNAvailableOrderBookList() = mutableListOf<AvailableOrderBook>()
    .apply {
        this@toMXNAvailableOrderBookList?.forEach {
            if (it.book?.contains("mxn") == true) this.add(it.toMXNAvailableOrderBook())
        }
    }

fun String?.toBookName(): String  = when(this) {
        BookType.BITCOIN.value -> Constants.BITCOIN_NAME
        BookType.ETHEREUM.value -> Constants.ETHEREUM_NAME
        BookType.XRP.value -> Constants.XRP_NAME
        BookType.LITECOIN.value -> Constants.LITECOIN_NAME
        BookType.BITCOIN_CASH.value -> Constants.BITCOIN_CASH_NAME
        BookType.TRUEUSD.value -> Constants.TRUE_USD_NAME
        BookType.DECETRALAND.value -> Constants.DECENTRALAND_NAME
        BookType.BASIC_ATENTION_TOKEN.value -> Constants.BASIC_ATTENTION_TOKEN_NAME
        BookType.DAI.value -> Constants.DAI_NAME
        BookType.USD_COIN.value -> Constants.USD_COIN_NAME
        else -> orEmpty()
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