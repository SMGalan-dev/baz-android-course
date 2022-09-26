package com.example.cripto_challenge.data.remote.dto.base

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
        AvailableOrderBook(
            book = this.book,
            minimum_amount = this.minimum_amount?.toDouble(),
            maximum_amount = this.maximum_amount?.toDouble(),
            minimum_price = this.minimum_price?.toDouble(),
            maximum_price = this.maximum_price?.toDouble(),
            minimum_value = this.minimum_value?.toDouble(),
            maximum_value = this.maximum_value?.toDouble()
        )
}
