package com.example.cripto_challenge.ui.available_books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.use_case.CurrencyUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**Dependency injection pattern, the class knows it needs it but cannot build it.
 * We generate a mechanism that can tell us how to save the instance given the dependency
 */
class AvailableBooksViewModel (private val currencyUseCase: CurrencyUseCase) : ViewModel() {

    private var _availableOrderBookList = MutableLiveData<List<AvailableOrderBook>>()
    private var _isLoading = MutableLiveData<Boolean>(true)

    val availableOrderBookList: LiveData<List<AvailableOrderBook>> get() = _availableOrderBookList
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getAvailableBooks(error: (info:String)->Unit) {
        currencyUseCase.getAvailableBooks().onEach {
            when(it) {
                is RequestState.Loading -> _isLoading.value = true
                is RequestState.Success -> {
                    _availableOrderBookList.value = it.data ?: emptyList()
                    _isLoading.value = false
                }
                is RequestState.Error -> {
                    error(it.message ?: "")
                    _isLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}