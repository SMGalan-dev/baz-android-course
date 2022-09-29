package com.example.cripto_challenge.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cripto_challenge.data.remote.BitsoServiceApi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.bitso.com/v3/"

    fun repository(): BitsoServiceApi = getRetrofit().create(BitsoServiceApi::class.java)

    private fun getRetrofit(): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val client: OkHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(HttpLoggingInterceptor().also {
                        it.setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                    ).build()
                val original = chain.request()
                val request: Request = original.newBuilder()
                    .header("User-Agent", original.url.host)
                    .method(original.method, original.body)
                    .build()
                client.newCall(request).execute()
            }.build()

}

class MyViewModelFactory<UC>(private val useCaseClass:UC): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(useCaseClass!!::class.java)
            .newInstance(useCaseClass)
}