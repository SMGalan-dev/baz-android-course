package com.example.cripto_challenge.common

import com.example.cripto_challenge.data.remote.BitsoServiceApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .setLenient()
        .create()

    private fun getRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.bitso.com/v3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    fun repository(): BitsoServiceApi = getRetrofit().create(BitsoServiceApi::class.java)

}