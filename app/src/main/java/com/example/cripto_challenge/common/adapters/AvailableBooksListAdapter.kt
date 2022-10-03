package com.example.cripto_challenge.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cripto_challenge.databinding.AvailableOrderBookItemBinding
import com.example.cripto_challenge.domain.model.AvailableOrderBook

class AvailableBooksListAdapter(
    val itemClick: (AvailableOrderBook?) -> Unit
) : ListAdapter<AvailableOrderBook, AvailableBooksListAdapter.ViewHolder>(difCallback) {

    companion object {
        val difCallback = object : DiffUtil.ItemCallback<AvailableOrderBook>() {
            override fun areItemsTheSame(oldItem: AvailableOrderBook, newItem: AvailableOrderBook): Boolean {
                return oldItem.book_code == newItem.book_code
            }
            override fun areContentsTheSame(oldItem: AvailableOrderBook, newItem: AvailableOrderBook): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = AvailableOrderBookItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindItem(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: AvailableOrderBookItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(item: AvailableOrderBook) {
            itemBinding.apply {
                availableBookCode.text = item.book_format_code
                availableOrderBookName.text = item.book_name
                availableOrderBookLogo.setImageDrawable(ContextCompat.getDrawable(itemBinding.root.context, item.book_logo ?: 0))
                itemView.setOnClickListener {
                    itemClick(item)
                }
            }
        }
    }
}
