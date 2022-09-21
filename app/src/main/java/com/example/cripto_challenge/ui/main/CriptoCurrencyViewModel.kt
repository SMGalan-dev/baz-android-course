package com.example.cripto_challenge.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cripto_challenge.common.utilities.toMXNAvailableOrderBookList
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.domain.model.Ticker
import com.example.cripto_challenge.domain.repository.BitsoServiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**Patron de ineccion de dependencias, la clase sabe que lo necesita pero no lo puede construir.
 * Generamos un mecanismo que nos pueda decir como salvar la instancia dada la dependencia
 */
class CriptoCurrencyViewModel (private val repository: BitsoServiceRepository) : ViewModel() {

    private var _availableOrderBookList = MutableLiveData<List<AvailableOrderBook>>()
    private var _selectedOrderBookName = MutableLiveData<String>()
    private var _ticker = MutableLiveData<Ticker>()
    private var _orderBook = MutableLiveData<OrderBook>()
    private var _isLoading = MutableLiveData<Boolean>(true)

    val availableOrderBookList: LiveData<List<AvailableOrderBook>> get() = _availableOrderBookList
    val selectedOrderBook: LiveData<String> get() = _selectedOrderBookName
    val ticker: LiveData<Ticker> get() = _ticker
    val OrderBook: LiveData<OrderBook> get() = _orderBook
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun setSelectedOrderBook(book: String){
        _selectedOrderBookName.value = book
    }

    fun getAvailableBooks(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAvaliableBooks()
            if (response.isSuccessful) {
                _availableOrderBookList.postValue(response.body()?.payload.toMXNAvailableOrderBookList())
            } else {
                println("Error")
            }

        }
    }

    fun getTicker(book: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getTicker(book = book)
            _isLoading.postValue(true)

            if (response.isSuccessful) {
                _ticker.postValue(response.body()?.payload?.toTicker() ?: Ticker())
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
                println(response.body()?.payload)
                _orderBook.postValue(response.body()?.payload?.toOrderBook() ?: OrderBook())
                _isLoading.postValue(false)

            } else {
                println("Error")
                _isLoading.postValue(false)
            }

        }
    }
}