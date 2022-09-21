package com.example.cripto_challenge.domain.model

data class AvailableOrderBook(
    var book: String? = null,
    var minimum_amount: Double? = null,
    var maximum_amount: Double? = null,
    var minimum_price: Double? = null,
    var maximum_price: Double? = null,
    var minimum_value: Double? = null,
    var maximum_value: Double? = null
)
