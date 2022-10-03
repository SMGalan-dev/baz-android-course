package com.example.cripto_challenge.data.remote.dto.response

import com.example.cripto_challenge.data.remote.dto.base.ErrorResponse
import com.example.cripto_challenge.data.remote.dto.base.OrderBookResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderBookBaseResponse (
    @SerializedName("success")
    @Expose
    var success: Boolean? = null,
    @SerializedName("payload")
    @Expose
    var orderBookData: OrderBookResponse? = null,
    @SerializedName("error")
    @Expose
    var error: ErrorResponse? = null
): Serializable


