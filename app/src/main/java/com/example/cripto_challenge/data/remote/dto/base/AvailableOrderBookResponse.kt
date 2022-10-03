package com.example.cripto_challenge.data.remote.dto.base

import com.example.cripto_challenge.R
import com.example.cripto_challenge.common.utilities.BookType
import com.example.cripto_challenge.common.utilities.toBookCodeFormat
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AvailableOrderBookResponse (
    @SerializedName("book")
    @Expose
    var book: String? = null,
    @SerializedName("minimum_amount")
    @Expose
    var minimum_amount: String? = null,
    @SerializedName("maximum_amount")
    @Expose
    var maximum_amount: String? = null,
    @SerializedName("minimum_price")
    @Expose
    var minimum_price: String? = null,
    @SerializedName("maximum_price")
    @Expose
    var maximum_price: String? = null,
    @SerializedName("minimum_value")
    @Expose
    var minimum_value: String? = null,
    @SerializedName("maximum_value")
    @Expose
    var maximum_value: String? = null
){
    fun toMXNAvailableOrderBook(): AvailableOrderBook =
        when(book) {
            BookType.BITCOIN.value -> AvailableOrderBook(
                book_code = book, book_name = "Bitcoin", book_format_code = book.toBookCodeFormat(), book_logo = R.drawable.ic_bitcoin_logo
            )
            BookType.ETHEREUM.value ->  AvailableOrderBook(
                book_code = book, book_name = "Ethereum", book_format_code = book.toBookCodeFormat(), book_logo = R.drawable.ic_ethereum_logo
            )
            BookType.XRP.value -> AvailableOrderBook(
                book_code = book, book_name = "XRP", book_format_code = book.toBookCodeFormat(), book_logo = R.drawable.ic_xrp_logo
            )
            BookType.LITECOIN.value -> AvailableOrderBook(
                book_code = book, book_name = "Litecoin", book_format_code = book.toBookCodeFormat(), book_logo = R.drawable.ic_litecoin_logo
            )
            BookType.BITCOIN_CASH.value -> AvailableOrderBook(
                book_code = book, book_name = "Bitcoin Cash", book_format_code = book.toBookCodeFormat(), book_logo = R.drawable.ic_bitcoin_cash_logo
            )
            BookType.TRUEUSD.value -> AvailableOrderBook(
                book_code = book, book_name = "True USD", book_format_code = book.toBookCodeFormat(), book_logo = R.drawable.ic_trueusd_logo
            )
            BookType.DECETRALAND.value -> AvailableOrderBook(
                book_code = book, book_name = "Decentraland", book_format_code = book.toBookCodeFormat(), book_logo = R.drawable.ic_decentraland_logo
            )
            BookType.BASIC_ATENTION_TOKEN.value -> AvailableOrderBook(
                book_code = book, book_name = "Basic Attention Token", book_format_code = book.toBookCodeFormat(), book_logo = R.drawable.ic_bat_logo
            )
            BookType.DAI.value -> AvailableOrderBook(
                book_code = book, book_name = "Dai", book_format_code = book.toBookCodeFormat(), book_logo = R.drawable.ic_dai_logo
            )
            BookType.USD_COIN.value -> AvailableOrderBook(
                book_code = book, book_name = "USD coin", book_format_code = book.toBookCodeFormat(), book_logo = R.drawable.ic_usd_coin_logo
            )
            else -> AvailableOrderBook()
        }
}
