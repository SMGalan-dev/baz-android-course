package com.example.cripto_challenge.domain.use_case

import android.content.Context
import com.example.cripto_challenge.R
import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.data.repository.CryptoCurrencyRepository
import com.example.cripto_challenge.domain.model.Ticker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTickerUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: CryptoCurrencyRepository
)
{
    operator fun invoke(book: String): Flow<RequestState<Ticker>> = flow {
        try {
            emit(RequestState.Loading())
            repository.getTicker(book)?.let {
                emit(RequestState.Success(it))
            } ?: run { emit(RequestState.Error(context.getString(R.string.ticker_error) + context.getString(R.string.no_data_internet_error)))}
        } catch (e: HttpException) {
            emit(RequestState.Error(context.getString(R.string.ticker_error) + e.localizedMessage))
        } catch (e: IOException) {
            emit(RequestState.Error(context.getString(R.string.ticker_error) + context.getString(R.string.interrupted_internet_error)))
        }
    }
}