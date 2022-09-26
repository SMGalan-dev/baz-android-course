package com.example.cripto_challenge.common.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cripto_challenge.common.utilities.toBookCodeFormat
import com.example.cripto_challenge.common.utilities.toBookName
import com.example.cripto_challenge.common.utilities.setLogo
import com.example.cripto_challenge.databinding.AvailableOrderBookItemBinding
import com.example.cripto_challenge.domain.model.AvailableOrderBook

class AvailableBooksAdapter(
    private var data: List<AvailableOrderBook> = emptyList<AvailableOrderBook>(),
    private val clickStore: (AvailableOrderBook?) -> Unit,
) : RecyclerView.Adapter<AvailableBooksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AvailableOrderBookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context: Context = holder.itemView.context
        //holder.bindItem(data[position])
        holder.bindItem(data[position], context)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val itemBinding: AvailableOrderBookItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(item: AvailableOrderBook, context: Context) {
            itemBinding.apply {
                availableBookCode.text = item.book?.toBookCodeFormat()
                availableOrderBookName.text = item.book.toBookName()
                availableOrderBookLogo.setImageDrawable(ContextCompat.getDrawable(context.applicationContext, setLogo(item.book ?: "")))

                itemView.setOnClickListener {
                    clickStore(item)
                }
            }
        }
    }

}

