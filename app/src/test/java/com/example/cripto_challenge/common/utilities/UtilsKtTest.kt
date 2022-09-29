package com.example.cripto_challenge.common.utilities

import com.example.cripto_challenge.R
import com.example.cripto_challenge.data.remote.dto.base.AvailableOrderBookResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import org.junit.Assert.*

import org.junit.Test

class UtilsKtTest {

    @Test
    fun `formatted book code to be displayed `() {
        //Given
        val bookCodeStr = "btc_mxn"

        //When
        val result = bookCodeStr.toBookCodeFormat()

        //Then
        assertEquals(result, "BTC/MXN")
    }

    @Test
    fun `formatted Double value into currency`() {
        //Given
        val bookCodeStr: Double = 300.00

        //When
        val result = bookCodeStr.formatAsCurrency()

        //Then
        assertEquals(result, "$300.00")
    }

    @Test
    fun toMXNAvailableOrderBookList() {
        //Given
        val bookCodeList: List<AvailableOrderBookResponse> =
            listOf(
                AvailableOrderBookResponse("btc_mxn"),
                AvailableOrderBookResponse("eth_mxn"),
                AvailableOrderBookResponse("btc_dll")
            )

        //When
        val result = bookCodeList.toMXNAvailableOrderBookList()

        //Then
        assert(result.contains(AvailableOrderBook(
            book_code = "btc_mxn",
            book_name = "Bitcoin",
            book_format_code = "BTC/MXN",
            book_logo = R.drawable.ic_bitcoin_logo
        )))
    }
}