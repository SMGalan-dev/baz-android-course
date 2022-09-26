package com.example.cripto_challenge.ui.available_books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cripto_challenge.R
import com.example.cripto_challenge.common.MyViewModelFactory
import com.example.cripto_challenge.common.RetrofitClient
import com.example.cripto_challenge.common.adapters.AvailableBooksAdapter
import com.example.cripto_challenge.data.repository.BitsoServiceRepositoryImp
import com.example.cripto_challenge.databinding.AvailableOrderBooksFragmentBinding
import com.example.cripto_challenge.domain.use_case.CurrencyUseCase

class AvailableOrderBooksFragment : Fragment() {

     private val criptoCurrencyVM by activityViewModels<AvailableBooksViewModel>(){ MyViewModelFactory(CurrencyUseCase(BitsoServiceRepositoryImp(RetrofitClient.repository()))) }
    private lateinit var binding: AvailableOrderBooksFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AvailableOrderBooksFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (criptoCurrencyVM.availableOrderBookList.value.isNullOrEmpty()) criptoCurrencyVM.getAvailableBooks(
            error ={Toast.makeText(context, it, Toast.LENGTH_SHORT).show()}
        )
        binding.apply {
            criptoCurrencyVM.isLoading.observe(viewLifecycleOwner){ isLoading ->
                if (isLoading) progressAvailableOrderBook.visibility = View.VISIBLE
                else {
                    recyclerAvailableBooks.adapter = criptoCurrencyVM.availableOrderBookList.value?.let { list ->
                        AvailableBooksAdapter(list){
                            val bundle = bundleOf("book" to it?.book_code)
                            findNavController().navigate(R.id.orderBookDetailFragment, bundle)
                        }
                    }
                    progressAvailableOrderBook.visibility = View.GONE
                }
            }
        }
    }
}