package com.example.cripto_challenge.domain.model

class OrderBook (
    var asks: List<OpenOrder>? = null,
    var bids: List<OpenOrder>? = null
)
