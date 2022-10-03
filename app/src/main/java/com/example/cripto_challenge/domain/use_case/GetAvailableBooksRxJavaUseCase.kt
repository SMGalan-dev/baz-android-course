package com.example.cripto_challenge.domain.use_case

import com.example.cripto_challenge.data.repository.CryptoCurrencyRepository
import javax.inject.Inject

class GetAvailableBooksRxJavaUseCase @Inject constructor(
    private val repository: CryptoCurrencyRepository
) {

    operator fun invoke() = repository.getAvailableBooksRxJava()
}
