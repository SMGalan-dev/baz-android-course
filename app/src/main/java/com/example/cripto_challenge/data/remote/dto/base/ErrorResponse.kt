package com.example.cripto_challenge.data.remote.dto.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    @Expose
    var code: String? = null,
    @SerializedName("message")
    @Expose
    var message: String? = null,
)
