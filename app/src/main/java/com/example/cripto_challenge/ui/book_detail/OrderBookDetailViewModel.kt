package com.example.cripto_challenge.ui.book_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.common.utilities.toBookCodeFormat
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker
import com.example.cripto_challenge.domain.use_case.CurrencyUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OrderBookDetailViewModel (private val currencyUseCase: CurrencyUseCase) : ViewModel() {

    private var _ticker = MutableLiveData<Ticker>()
    private var _orderBook = MutableLiveData<OrderBook>()
    private var _isLoading = MutableLiveData<Boolean>(true)

    val ticker: LiveData<Ticker> get() = _ticker
    val orderBook: LiveData<OrderBook> get() = _orderBook
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getTicker(book: String, error: (info:String)->Unit) {
        currencyUseCase.getTicker(book = book).onEach { state ->
            when(state) {
                is RequestState.Loading -> _isLoading.value = true
                is RequestState.Success -> {
                    _ticker.value = state.data ?: Ticker()
                    getOrderBook(book) {
                        error(it)
                    }
                }
                is RequestState.Error -> {
                    error(state.message ?: "")
                    getOrderBook(book) {
                        error( "Ticker error: ${state.message}" + "\n" + it)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getOrderBook(book: String, error: (info:String)->Unit) {
        currencyUseCase.getOrderBook(book = book).onEach {
            when(it) {
                is RequestState.Loading -> _isLoading.value = true
                is RequestState.Success -> {
                    _orderBook.value = it.data ?: OrderBook()
                    _isLoading.value = false
                }
                is RequestState.Error -> {
                    error( "Bids/Asks error: ${it.message}")
                    _isLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}