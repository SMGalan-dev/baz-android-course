package com.example.cripto_challenge.domain.use_case

import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.data.repository.CryptoCurrencyRepository
import com.example.cripto_challenge.domain.model.Ticker
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTickerUseCase @Inject constructor(private val repository: CryptoCurrencyRepository) {

    operator fun invoke(book: String): Flow<RequestState<Ticker>> = repository.getTicker(book)
}
