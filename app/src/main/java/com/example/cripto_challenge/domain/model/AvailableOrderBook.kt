package com.example.cripto_challenge.domain.model

data class AvailableOrderBook(
    var book_code: String? = null,
    var book_name: String? = null,
    var book_format_code: String? = null,
    var book_logo: Int? = null
)