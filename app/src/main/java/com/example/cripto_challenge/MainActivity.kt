package com.example.cripto_challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cripto_challenge.data.remote.BitsoServiceApi
import com.example.cripto_challenge.databinding.MainActivityBinding
import com.example.cripto_challenge.domain.repository.BitsoServiceRepository
import com.example.cripto_challenge.ui.main.CriptoCurrencyViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    //private val CriptoCurrencyVM by viewModels<CriptoCurrencyViewModel>(){ MyViewModelFactoryRep(BitsoServiceRepositoryImp(RetrofitClient().repository())) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

class MyViewModelFactoryRep(val param: BitsoServiceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CriptoCurrencyViewModel(param) as T
}