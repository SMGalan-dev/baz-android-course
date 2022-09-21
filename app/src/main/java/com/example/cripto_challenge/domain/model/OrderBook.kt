package com.example.cripto_challenge.domain.model

import java.util.*

class OrderBook (
    var asks: List<OpenOrder>? = null,
    var bids: List<OpenOrder>? = null,
    var updated_at: Date? = null,
    var sequence: Int? = null
)
