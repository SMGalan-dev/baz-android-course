package com.example.cripto_challenge.ui.book_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cripto_challenge.MainActivity
import com.example.cripto_challenge.R
import com.example.cripto_challenge.common.adapters.OpenOrderListAdapter
import com.example.cripto_challenge.common.utilities.toBookCodeFormat
import com.example.cripto_challenge.common.utilities.toBookName
import com.example.cripto_challenge.databinding.OrderBookDetailFragmentBinding

class OrderBookDetailFragment : Fragment() {

    private val orderBookDetailVM by activityViewModels<OrderBookDetailViewModel>()
    private lateinit var binding: OrderBookDetailFragmentBinding

    private lateinit var bookCode: String
    private val bidsListAdapter: OpenOrderListAdapter by lazy {OpenOrderListAdapter()}
    private val askListAdapter: OpenOrderListAdapter by lazy {OpenOrderListAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OrderBookDetailFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookCode = arguments?.getString(getString(R.string.book_code)).orEmpty()
        orderBookDetailVM.getTicker(bookCode,
            error ={
                (activity as MainActivity).noNetworkConnection(it)
            }
        )

        binding.apply {
            orderBookDetailVM.isLoading.observe(viewLifecycleOwner) {
                if (it) progressDetailOrderBook.visibility = View.VISIBLE
                else {
                    orderBookName.text = bookCode.toBookName()
                    tvBookCode.text = orderBookDetailVM.ticker.value?.book.toBookCodeFormat()
                    bookLastPrice.text = orderBookDetailVM.ticker.value?.last
                    bookHighPrice.text = orderBookDetailVM.ticker.value?.high
                    bookLowPrice.text = orderBookDetailVM.ticker.value?.low

                    recyclerOrderAsks.adapter = askListAdapter.also { askAdapter ->
                        askAdapter.submitList(orderBookDetailVM.orderBook.value?.asks)
                    }

                    recyclerOrderBids.adapter = bidsListAdapter.also { bidsAdapter ->
                        bidsAdapter.submitList(orderBookDetailVM.orderBook.value?.bids)
                    }
                    progressDetailOrderBook.visibility = View.GONE
                }
            }
        }
    }
}