package com.example.cripto_challenge.data.remote.dto.base

import com.example.cripto_challenge.R
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import org.junit.Test

class AvailableOrderBookResponseTest {

    lateinit var systemUnderTest: AvailableOrderBookResponse

    @Test
    fun `Convertion of an Response to required MXNAvailableOrderBook`() {
        // Given
        systemUnderTest = AvailableOrderBookResponse(
            "btc_mxn",
            "0.00000030000",
            "3000",
            "40000",
            "20000000",
            "10.00",
            "200000000",
        )

        // When
        val result = systemUnderTest.toMXNAvailableOrderBook()

        // Then
        assert(
            result == AvailableOrderBook(
                book_code = "btc_mxn",
                book_name = "Bitcoin",
                book_format_code = "BTC/MXN",
                book_logo = R.drawable.ic_bitcoin_logo
            )
        )
    }

    @Test
    fun `Convertion of Response without mxn required to MXNAvailableOrderBook`() {
        // Given
        systemUnderTest = AvailableOrderBookResponse(
            "btc_abc",
            "0.00000030000",
            "3000",
            "40000",
            "20000000",
            "10.00",
            "200000000",
        )

        // When
        val result = systemUnderTest.toMXNAvailableOrderBook()

        // Then
        assert(
            result == AvailableOrderBook()
        )
    }
}
