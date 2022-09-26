package com.example.cripto_challenge.ui.book_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cripto_challenge.R
import com.example.cripto_challenge.common.MyViewModelFactory
import com.example.cripto_challenge.common.RetrofitClient
import com.example.cripto_challenge.common.adapters.OpenOrderAdapter
import com.example.cripto_challenge.common.utilities.toBookCodeFormat
import com.example.cripto_challenge.common.utilities.toBookName
import com.example.cripto_challenge.data.repository.BitsoServiceRepositoryImp
import com.example.cripto_challenge.databinding.OrderBookDetailFragmentBinding
import com.example.cripto_challenge.domain.model.OpenOrder
import com.example.cripto_challenge.domain.use_case.CurrencyUseCase

class OrderBookDetailFragment : Fragment() {

    private val orderBookDetailVM by viewModels<OrderBookDetailViewModel>(){ MyViewModelFactory(CurrencyUseCase(BitsoServiceRepositoryImp(RetrofitClient.repository()))) }
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
        orderBookDetailVM.getTicker(arguments?.getString("book") ?: "",
            error ={ Toast.makeText(context, it, Toast.LENGTH_SHORT).show()}
        )
        binding.apply {
            orderBookDetailVM.isLoading.observe(viewLifecycleOwner) {
                if (it) progressDetailOrderBook.visibility = View.VISIBLE
                else {
                    orderBookName.text = orderBookDetailVM.ticker.value?.book.toBookName()
                    tvBookCode.text = orderBookDetailVM.ticker.value?.book.toBookCodeFormat()
                    bookLastPrice.text = orderBookDetailVM.ticker.value?.last
                    bookHighPrice.text = orderBookDetailVM.ticker.value?.high
                    bookLowPrice.text = orderBookDetailVM.ticker.value?.low

                    recyclerOrderAsks.adapter = OpenOrderAdapter(orderBookDetailVM.orderBook.value?.asks ?: emptyList<OpenOrder>())
                    recyclerOrderBids.adapter = OpenOrderAdapter(orderBookDetailVM.orderBook.value?.bids ?: emptyList<OpenOrder>())
                    progressDetailOrderBook.visibility = View.GONE
                }
            }
        }
    }
}