package com.example.cripto_challenge.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cripto_challenge.MyViewModelFactoryRep
import com.example.cripto_challenge.R
import com.example.cripto_challenge.common.RetrofitClient
import com.example.cripto_challenge.common.adapters.OpenOrderAdapter
import com.example.cripto_challenge.data.repository.BitsoServiceRepositoryImp
import com.example.cripto_challenge.databinding.OrderBookDetailFragmentBinding
import com.example.cripto_challenge.domain.model.OpenOrder

class OrderBookDetailFragment : Fragment() {

    private val CriptoCurrencyVM by activityViewModels<CriptoCurrencyViewModel>(){ MyViewModelFactoryRep(BitsoServiceRepositoryImp(RetrofitClient.repository())) }
    private lateinit var binding: OrderBookDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OrderBookDetailFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.availableOrderBooksFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CriptoCurrencyVM.getTicker(CriptoCurrencyVM.selectedOrderBook.value ?: "")
        binding.apply {
            CriptoCurrencyVM.isLoading.observe(viewLifecycleOwner) {
                orderBookName.text = CriptoCurrencyVM.selectedOrderBook.value
                bookLastPrice.text = "Último: ${CriptoCurrencyVM.ticker.value?.last ?: ""}"
                bookHighPrice.text = CriptoCurrencyVM.ticker.value?.high ?: ""
                bookLowPrice.text = CriptoCurrencyVM.ticker.value?.low ?: ""

                recyclerOrderAsks.adapter = OpenOrderAdapter(CriptoCurrencyVM.OrderBook.value?.asks ?: emptyList<OpenOrder>())
                recyclerOrderBids.adapter = OpenOrderAdapter(CriptoCurrencyVM.OrderBook.value?.bids ?: emptyList<OpenOrder>())
            }
        }
    }
}