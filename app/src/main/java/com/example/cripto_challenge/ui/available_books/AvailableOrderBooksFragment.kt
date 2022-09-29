package com.example.cripto_challenge.ui.available_books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cripto_challenge.MainActivity
import com.example.cripto_challenge.R
import com.example.cripto_challenge.common.MyViewModelFactory
import com.example.cripto_challenge.common.RetrofitClient
import com.example.cripto_challenge.common.adapters.AvailableBooksListAdapter
import com.example.cripto_challenge.config.InitApplication.Companion.criptoCurrencyDB
import com.example.cripto_challenge.data.repository.BitsoServiceRepositoryImp
import com.example.cripto_challenge.databinding.AvailableOrderBooksFragmentBinding
import com.example.cripto_challenge.domain.use_case.CurrencyUseCase

class AvailableOrderBooksFragment : Fragment() {

    private val criptoCurrencyVM by viewModels<AvailableBooksViewModel>(){
        MyViewModelFactory(CurrencyUseCase(BitsoServiceRepositoryImp(RetrofitClient.repository(),criptoCurrencyDB.getCriptoCurrencyDao())))
    }
    private lateinit var binding: AvailableOrderBooksFragmentBinding

    private val availableBooksAdapterList: AvailableBooksListAdapter by lazy {
        AvailableBooksListAdapter {
            val bundle = bundleOf("book" to it?.book_code)
            findNavController().navigate(R.id.orderBookDetailFragment, bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AvailableOrderBooksFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        criptoCurrencyVM.getAvailableBooks(
            error ={(activity as MainActivity).noNetworkConnection(it)}
        )

        binding.apply {
            criptoCurrencyVM.isLoading.observe(viewLifecycleOwner){ isLoading ->
                if (isLoading) progressAvailableOrderBook.visibility = View.VISIBLE
                else {
                    recyclerAvailableBooks.adapter = availableBooksAdapterList.also {
                        it.submitList(criptoCurrencyVM.availableOrderBookList.value)
                    }
                    progressAvailableOrderBook.visibility = View.GONE
                }
            }
        }
    }
}