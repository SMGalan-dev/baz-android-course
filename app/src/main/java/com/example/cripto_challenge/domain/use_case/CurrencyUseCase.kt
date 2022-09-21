package com.example.cripto_challenge.domain.use_case

import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksResponse
import com.example.cripto_challenge.domain.repository.BitsoServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CurrencyUseCase (private val repository: BitsoServiceRepository) {

    fun getAvailableBooks(book: String): Flow<AvailableBooksResponse?> = flow {
        try {
            val response = repository.getTicker(book = book)
        } catch (e: IOException) {

        }
    }
}