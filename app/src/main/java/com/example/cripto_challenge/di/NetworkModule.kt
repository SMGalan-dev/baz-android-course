package com.example.cripto_challenge.di

import com.example.cripto_challenge.data.remote.BitsoServiceApi
import com.example.cripto_challenge.data.repository.CryptoCurrencyRepository
import com.example.cripto_challenge.data.repository.CryptoCurrencyRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    abstract fun provideCryptoRepository(cryptoCurrencyRepositoryImp: CryptoCurrencyRepositoryImp): CryptoCurrencyRepository

    companion object{

        private const val BASE_URL = "https://api.bitso.com/v3/"

        private val okHttpClient: OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val client: OkHttpClient = OkHttpClient.Builder()
                        .addNetworkInterceptor(HttpLoggingInterceptor().also {
                            it.setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                        )
                        .callTimeout(5, TimeUnit.SECONDS)
                        .build()
                    val original = chain.request()
                    val request: Request = original.newBuilder()
                        .header("User-Agent", original.url.host)
                        .method(original.method, original.body)
                        .build()
                    client.newCall(request).execute()
                }.build()

        @Singleton
        @Provides
        fun getRetrofit():Retrofit =
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //RxJava
                .build()

        @Singleton
        @Provides
        fun repository(retrofit: Retrofit): BitsoServiceApi =
            retrofit.create(BitsoServiceApi::class.java)

    }
}