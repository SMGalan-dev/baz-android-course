package com.example.cripto_challenge.domain.use_case

import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.domain.repository.BitsoServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

//Aqui va toda la logica de negocio
class CurrencyUseCase (private val repository: BitsoServiceRepository) {

    /*
    fun getAvailableBooks(book: String): Flow<AvailableBooksBaseResponse?> = flow {
        try {
            val response = repository.getTicker(book = book)
        } catch (e: IOException) {

        }
    }

    //TODO this is a demo for the class
    //suspend fun getAvailableBooks(): AvailableBooksResponse = repository.loadCharacters()
    //suspend operator fun invoke(): AvailableBooksResponse = repository.loadCharacters() //Para solo una funcion

     */

    fun getAvailableBooks(book: String): Flow<AvailableBooksBaseResponse?> = flow {
        try {
            val response = repository.getTicker(book = book)
        } catch (e: IOException) {

        }
    }


}