package com.example.cripto_challenge.domain.use_case

import com.example.cripto_challenge.data.repository.CryptoCurrencyRepository
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import javax.inject.Inject

class UpdateAvailableBooksDBUseCase @Inject constructor(
    private val repository: CryptoCurrencyRepository
) {

    operator fun invoke(list: List<AvailableOrderBook>) = repository.updateAvailableOrderBookDatabase(list)
}
