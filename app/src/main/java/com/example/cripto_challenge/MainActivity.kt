package com.example.cripto_challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cripto_challenge.databinding.MainActivityBinding
import com.example.cripto_challenge.domain.repository.BitsoServiceRepository
import com.example.cripto_challenge.ui.available_books.AvailableBooksViewModel
import com.example.cripto_challenge.ui.book_detail.OrderBookDetailViewModel

class MainActivity : AppCompatActivity() {

    /*
    //MANUAL INJECTION Con esto no se esta revisando el ciclo de vida, etc
    private val CriptoCurrencyVM by viewModels<CriptoCurrencyViewModel>(){
        MyViewModelFactoryRep(
            useCase(
                BitsoServiceRepositoryImp(
                    RetrofitClient.repository()
                )
            )
        )
    }
     */

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

class MyViewModelFactoryRep(val param: BitsoServiceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = AvailableBooksViewModel(param) as T
}

class MyViewModelFactoryBookDetail(val param: BitsoServiceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = OrderBookDetailViewModel(param) as T
}

/** MANUAL INJECTION
 * Function with dependences for manual dependences injection
 *  set to viewmodel with use case constructor
 *  Usually one Factory for each VM
class MyExampleViewModelFactory(val useCase: CurrencyUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CriptoCurrencyViewModel(useCase) as T
}
 */