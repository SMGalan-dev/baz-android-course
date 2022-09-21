package com.example.cripto_challenge.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cripto_challenge.MyViewModelFactoryRep
import com.example.cripto_challenge.R
import com.example.cripto_challenge.common.RetrofitClient
import com.example.cripto_challenge.common.adapters.AvailableBooksAdapter
import com.example.cripto_challenge.data.repository.BitsoServiceRepositoryImp
import com.example.cripto_challenge.databinding.AvailableOrderBooksFragmentBinding

class AvailableOrderBooksFragment : Fragment() {

    private val CriptoCurrencyVM by activityViewModels<CriptoCurrencyViewModel>(){ MyViewModelFactoryRep(BitsoServiceRepositoryImp(RetrofitClient.repository())) }
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
        CriptoCurrencyVM.getAvailableBooks()
        binding.apply {
            CriptoCurrencyVM.availableOrderBookList.observe(viewLifecycleOwner) { productList ->
                recyclerAvailableBooks.adapter = AvailableBooksAdapter(productList){
                    CriptoCurrencyVM.setSelectedOrderBook(it?.book ?: "vacio")
                    findNavController().navigate(R.id.orderBookDetailFragment)
                }
            }
        }
    }
}