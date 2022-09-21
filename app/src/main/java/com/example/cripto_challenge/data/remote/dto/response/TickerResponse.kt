package com.example.cripto_challenge.data.remote.dto.response

import com.example.cripto_challenge.data.remote.dto.base.TickerDto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TickerResponse (
    @SerializedName("success")
    @Expose
    var success: Boolean? = null,
    @SerializedName("payload")
    @Expose
    var payload: TickerDto? = null
): Serializable

