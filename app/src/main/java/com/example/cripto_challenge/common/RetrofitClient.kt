package com.example.cripto_challenge.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cripto_challenge.data.remote.BitsoServiceApi
import com.example.cripto_challenge.domain.repository.BitsoServiceRepository
import com.example.cripto_challenge.ui.book_detail.OrderBookDetailViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.bitso.com/v3/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().also { it.setLevel(HttpLoggingInterceptor.Level.BODY) })
        .build()

    private fun getRetrofit(): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun repository(): BitsoServiceApi = getRetrofit().create(BitsoServiceApi::class.java)
}

class MyViewModelFactoryBookDetail(val param: BitsoServiceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = OrderBookDetailViewModel(param) as T
}

class MyViewModelFactory<UC>(private val useCaseClass:UC): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(useCaseClass!!::class.java)
            .newInstance(useCaseClass)
}

/** MANUAL INJECTION
 * Function with dependences for manual dependences injection
 *  set to viewmodel with use case constructor
 *  Usually one Factory for each VM

class MyViewModelFactoryRep(private val useCaseClass:CurrencyUseCase):ViewModelProvider.Factory {
override fun <T : ViewModel> create(modelClass: Class<T>): T = AvailableBooksViewModel(useCaseClass) as T
}
 */