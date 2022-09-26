package com.example.cripto_challenge.domain.use_case

import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.common.utilities.toMXNAvailableOrderBookList
import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.repository.BitsoServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

//Here goes all the business logic
class CurrencyUseCase (private val repository: BitsoServiceRepository) {

    /*
    //TODO this is a demo for the class
    //suspend fun getAvailableBooks(): AvailableBooksResponse = repository.loadCharacters()
    //suspend operator fun invoke(): AvailableBooksResponse = repository.loadCharacters() //for one function only
     */
    fun getAvailableBooks(): Flow<RequestState<List<AvailableOrderBook>>> = flow {
        try {
            emit(RequestState.Loading<List<AvailableOrderBook>>())
            val availableBooks: List<AvailableOrderBook> = repository.getAvaliableBooks().let {
                (it.body() as AvailableBooksBaseResponse).availableBooksListData.toMXNAvailableOrderBookList()
            }
            emit(RequestState.Success(availableBooks))
        } catch (e: HttpException) {
            emit(RequestState.Error<List<AvailableOrderBook>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(RequestState.Error<List<AvailableOrderBook>>("Couldn't reach server. Check your internet connection."))
        }
    }
}