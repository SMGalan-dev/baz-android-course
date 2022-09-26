package com.example.cripto_challenge.ui.book_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker
import com.example.cripto_challenge.domain.repository.BitsoServiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderBookDetailViewModel (private val repository: BitsoServiceRepository) : ViewModel() {

    private var _ticker = MutableLiveData<Ticker>()
    private var _orderBook = MutableLiveData<OrderBook>()
    private var _isLoading = MutableLiveData<Boolean>(true)

    val ticker: LiveData<Ticker> get() = _ticker
    val OrderBook: LiveData<OrderBook> get() = _orderBook
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getTicker(book: String){
        _isLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getTicker(book = book)

            if (response.isSuccessful) {
                _ticker.postValue(response.body()?.tickerData?.toTicker() ?: Ticker())
                getOrderBook(book)
                //_isLoading.postValue(false)
            } else {
                println("Error")
                _isLoading.postValue(false)
            }

        }
    }

    fun getOrderBook(book: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getOrderBook(book = book)

            if (response.isSuccessful) {
                println(response.body()?.orderBookData)
                _orderBook.postValue(response.body()?.orderBookData?.toOrderBook() ?: OrderBook())
                _isLoading.postValue(false)

            } else {
                println("Error")
                _isLoading.postValue(false)
            }

        }
    }
}