package com.example.cripto_challenge.domain.model

class OrderBook (
    var book: String? = null,
    var asks: List<OpenOrder>? = null,
    var bids: List<OpenOrder>? = null
)
