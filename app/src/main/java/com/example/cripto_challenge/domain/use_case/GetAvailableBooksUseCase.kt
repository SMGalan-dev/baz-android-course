package com.example.cripto_challenge.domain.use_case

import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.data.repository.CryptoCurrencyRepository
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAvailableBooksUseCase @Inject constructor(private val repository: CryptoCurrencyRepository) {

    operator fun invoke(): Flow<RequestState<List<AvailableOrderBook>>> = repository.getAvailableBooks()
}
