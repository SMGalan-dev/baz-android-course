package com.example.cripto_challenge.domain.use_case

import android.util.Log
import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.common.utilities.toAvailableOrderBookEntityList
import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity
import com.example.cripto_challenge.data.repository.CryptoCurrencyRepository
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class GetAvailableBooksUseCase(private val repository: CryptoCurrencyRepository) {

    private fun getAvailableBooks(): Flow<RequestState<List<AvailableOrderBook>>> = flow {
        try {
            emit(RequestState.Loading())
            repository.getAvailableBooks().let {
                updateAvailableBooksDB(it.toAvailableOrderBookEntityList())
                emit(RequestState.Success(it))
            }
        } catch (e: HttpException) {
            emit(RequestState.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            repository.getAllCryptoCurrencyFromDatabase()?.let {
                emit(RequestState.Error("Couldn't reach server. Check your internet connection. \nShowing stored data", it))
            } ?: run{
                emit(RequestState.Error("Couldn't reach server. Check your internet connection. \nNo stored data"))
            }}
    }

    private fun updateAvailableBooksDB(bookList: List<AvailableOrderBookEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAllCryptoCurrencyFromDatabase().run {
                if (this.isNullOrEmpty()){
                    Log.i("CriptoCurrencyDataBase", "AvailableOrderBookEntity inserted")
                    repository.insertCryptoCurrencyToDatabase(bookList)
                } else{
                    Log.i("CriptoCurrencyDataBase", "AvailableOrderBookEntity updated")
                    repository.updateCryptoCurrencyToDatabase(bookList)
                }
            }
        }
    }

    operator fun invoke() = getAvailableBooks()

}