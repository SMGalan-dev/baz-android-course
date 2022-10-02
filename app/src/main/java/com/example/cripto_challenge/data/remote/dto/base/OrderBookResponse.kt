package com.example.cripto_challenge.data.remote.dto.base

import com.example.cripto_challenge.domain.model.OpenOrder
import com.example.cripto_challenge.domain.model.OrderBook
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


data class OrderBookResponse (
    @SerializedName("asks")
    @Expose
    var asks: List<OpenOrderResponse>? = null,
    @SerializedName("bids")
    @Expose
    var bids: List<OpenOrderResponse>? = null,
    @SerializedName("updated_at")
    @Expose
    var updated_at: Date? = null,
    @SerializedName("sequence")
    @Expose
    var sequence: String? = null
){
    fun toOrderBook(): OrderBook =
        OrderBook(
            asks = asks.toOrderBookList,
            bids = bids.toOrderBookList
        )

    private val List<OpenOrderResponse>?.toOrderBookList: List<OpenOrder>
        get() = mutableListOf<OpenOrder>()
            .apply {
                this@toOrderBookList?.forEach {
                    this.add(it.toOpenOrder() )
                }
            }
}
