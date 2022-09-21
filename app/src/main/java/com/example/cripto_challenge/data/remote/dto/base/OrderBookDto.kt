package com.example.cripto_challenge.data.remote.dto.base

import com.example.cripto_challenge.domain.model.OpenOrder
import com.example.cripto_challenge.domain.model.OrderBook
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


data class OrderBookDto (
    @SerializedName("asks")
    @Expose
    var asks: List<OpenOrderDto>? = null,
    @SerializedName("bids")
    @Expose
    var bids: List<OpenOrderDto>? = null,
    @SerializedName("updated_at")
    @Expose
    var updated_at: Date? = null,
    @SerializedName("sequence")
    @Expose
    var sequence: Int? = null
){
    fun toOrderBook(): OrderBook =
        OrderBook(
            asks = asks.toOrderBookList,
            bids = bids.toOrderBookList,
            updated_at = updated_at,
            sequence = sequence
        )

    private val List<OpenOrderDto>?.toOrderBookList: List<OpenOrder>
        get() = mutableListOf<OpenOrder>()
            .apply {
                this@toOrderBookList?.forEach {
                    this.add(it.toOpenOrder() )
                }
            }
}
