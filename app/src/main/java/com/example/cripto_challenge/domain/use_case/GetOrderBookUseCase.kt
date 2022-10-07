package com.example.cripto_challenge.domain.use_case

import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.data.repository.CryptoCurrencyRepository
import com.example.cripto_challenge.domain.model.OrderBook
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderBookUseCase @Inject constructor(private val repository: CryptoCurrencyRepository) {

    operator fun invoke(book: String): Flow<RequestState<OrderBook>> = repository.getOrderBook(book)
}
