package com.example.cripto_challenge.domain.use_case

import androidx.lifecycle.MutableLiveData
import com.example.cripto_challenge.data.repository.CryptoCurrencyRepository
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import javax.inject.Inject

class GetAvailableBooksRxJavaUseCase @Inject constructor(
    private val repository: CryptoCurrencyRepository
) {
    operator fun invoke(): MutableLiveData<List<AvailableOrderBook>> =
        repository.getAvailableBooksRxJava()
}
