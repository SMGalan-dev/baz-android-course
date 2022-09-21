package com.example.cripto_challenge.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cripto_challenge.databinding.OpenOrderItemBinding
import com.example.cripto_challenge.domain.model.OpenOrder

class OpenOrderAdapter(
    private var data: List<OpenOrder> = emptyList<OpenOrder>()
) : RecyclerView.Adapter<OpenOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OpenOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val itemBinding: OpenOrderItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(item: OpenOrder) {
            itemBinding.apply {
                openOrderPrice.text = item.price.toString()
                openOrderAmount.text = item.amount.toString()
            }
        }
    }

}

