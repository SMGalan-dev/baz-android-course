package com.example.cripto_challenge.data.remote.dto.response

import com.example.cripto_challenge.data.remote.dto.base.AvailableOrderBookResponse
import com.example.cripto_challenge.data.remote.dto.base.ErrorResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AvailableBooksBaseResponse(
    @SerializedName("success")
    @Expose
    var success: Boolean? = null,
    @SerializedName("payload")
    @Expose
    var availableBooksListData: List<AvailableOrderBookResponse>? = null,
    @SerializedName("error")
    @Expose
    var error: ErrorResponse? = null
)
