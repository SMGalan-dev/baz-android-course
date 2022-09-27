package com.example.cripto_challenge

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cripto_challenge.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun noNetworkConnection(info: String){
        binding.tvErrorInfo.text = info
        binding.layoutErrorOcurred.visibility = View.VISIBLE
        binding.btnCloseErrorMessage.setOnClickListener {
            closeErrorMessage()
        }
    }

    fun closeErrorMessage() {
        binding.layoutErrorOcurred.visibility = View.GONE
    }
}