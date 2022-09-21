package com.example.cripto_challenge.data.remote.dto.base

import com.example.cripto_challenge.domain.model.OpenOrder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class OpenOrderDto (
    @SerializedName("book")
    @Expose
    var book: String? = null,
    @SerializedName("price")
    @Expose
    var price: Double? = null,
    @SerializedName("amount")
    @Expose
    var amount: Double? = null
){
    fun toOpenOrder(): OpenOrder =
        OpenOrder(
            book = book,
            price = price,
            amount = amount
        )
}