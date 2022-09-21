package com.example.cripto_challenge.data.remote.dto.response

import com.example.cripto_challenge.data.remote.dto.base.AvailableOrderBookDto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AvailableBooksResponse (
    @SerializedName("success")
    @Expose
    var success: Boolean? = null,
    @SerializedName("payload")
    @Expose
    var payload: List<AvailableOrderBookDto>? = null
)


