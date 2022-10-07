package com.example.cripto_challenge.domain.use_case

import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.data.repository.CryptoCurrencyRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAvailableBooksRxJavaUseCase @Inject constructor(
    private val repository: CryptoCurrencyRepository
) {

    operator fun invoke(): Single<AvailableBooksBaseResponse> = repository.getAvailableBooksRxJava()
}
