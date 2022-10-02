package com.example.cripto_challenge.ui.available_books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.use_case.GetAvailableBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AvailableBooksViewModel @Inject constructor(private val currencyUseCase: GetAvailableBooksUseCase) : ViewModel() {

    private var _availableOrderBookList = MutableLiveData<List<AvailableOrderBook>>()
    private var _isLoading = MutableLiveData(true)

    val availableOrderBookList: LiveData<List<AvailableOrderBook>> get() = _availableOrderBookList
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getAvailableBooks(error: (info:String)->Unit) {
        currencyUseCase.invoke().onEach { state ->
            when(state) {
                is RequestState.Loading -> _isLoading.value = true
                is RequestState.Success -> {
                    _availableOrderBookList.value = state.data.orEmpty()
                    _isLoading.value = false
                }
                is RequestState.Error -> {
                    error(state.message.orEmpty())
                    _availableOrderBookList.value = emptyList()
                    _isLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}