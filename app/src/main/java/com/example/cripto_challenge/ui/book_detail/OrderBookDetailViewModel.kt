package com.example.cripto_challenge.ui.book_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker
import com.example.cripto_challenge.domain.use_case.GetOrderBookUseCase
import com.example.cripto_challenge.domain.use_case.GetTickerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrderBookDetailViewModel @Inject constructor(
    private val tickerUseCase: GetTickerUseCase,
    private val orderBookUseCase: GetOrderBookUseCase
    ): ViewModel()
{

    private var _ticker = MutableLiveData<Ticker>()
    private var _orderBook = MutableLiveData<OrderBook>()
    private var _isLoading = MutableLiveData(true)

    val ticker: LiveData<Ticker> get() = _ticker
    val orderBook: LiveData<OrderBook> get() = _orderBook
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getTicker(book: String, error: (info:String)->Unit) {
        tickerUseCase.invoke(book = book).onEach { state ->
            when(state) {
                is RequestState.Loading -> _isLoading.value = true
                is RequestState.Success -> {
                    _ticker.value = state.data ?: Ticker()
                    getOrderBook(book) {
                        error(it)
                    }
                }
                is RequestState.Error -> {
                    _ticker.value = state.data ?: Ticker()
                    error(state.message.orEmpty())
                    getOrderBook(book) {
                        error( state.message + "\n" + it)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getOrderBook(book: String, error: (info:String)->Unit) {
        orderBookUseCase.invoke(book = book).onEach {
            when(it) {
                is RequestState.Loading -> _isLoading.value = true
                is RequestState.Success -> {
                    _orderBook.value = it.data ?: OrderBook()
                    _isLoading.value = false
                }
                is RequestState.Error -> {
                    _orderBook.value = it.data ?: OrderBook()
                    error(it.message.orEmpty())
                    _isLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}