package com.example.cripto_challenge.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cripto_challenge.databinding.OpenOrderItemBinding
import com.example.cripto_challenge.domain.model.OpenOrder

class OpenOrderListAdapter(): ListAdapter<OpenOrder, OpenOrderListAdapter.ViewHolder>(difCallback){
    
    companion object{
        val difCallback = object : DiffUtil.ItemCallback<OpenOrder>(){
            override fun areItemsTheSame(oldItem: OpenOrder, newItem: OpenOrder): Boolean {
                return oldItem.amount == newItem.amount
            }
            override fun areContentsTheSame(oldItem: OpenOrder, newItem: OpenOrder): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = OpenOrderItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindItem(getItem(position))
    }


    inner class ViewHolder(private val itemBinding: OpenOrderItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(item: OpenOrder) {
            itemBinding.apply {
                openOrderPrice.text = item.price.toString()
                openOrderAmount.text = item.amount.toString()
            }
        }
    }
}
