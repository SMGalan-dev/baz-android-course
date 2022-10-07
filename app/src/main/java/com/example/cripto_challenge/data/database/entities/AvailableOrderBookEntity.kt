package com.example.cripto_challenge.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cripto_challenge.data.remote.dto.base.AvailableOrderBookResponse
import com.example.cripto_challenge.data.remote.dto.base.ErrorResponse
import com.example.cripto_challenge.data.remote.dto.response.AvailableBooksBaseResponse
import com.example.cripto_challenge.domain.model.AvailableOrderBook

@Entity(tableName = "available_order_book_table")
data class AvailableOrderBookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "book_code") var book_code: String? = null,
    @ColumnInfo(name = "book_name") var book_name: String? = null,
    @ColumnInfo(name = "book_format_code") var book_format_code: String? = null,
    @ColumnInfo(name = "book_logo") var book_logo: Int? = null
)

fun List<AvailableOrderBook>?.toAvailableOrderBookEntityList() = mutableListOf<AvailableOrderBookEntity>()
    .apply {
        this@toAvailableOrderBookEntityList?.forEach {
            this.add(
                AvailableOrderBookEntity(
                    book_code = it.book_code,
                    book_name = it.book_name,
                    book_format_code = it.book_format_code,
                    book_logo = it.book_logo
                )
            )
        }
    }

fun List<AvailableOrderBookEntity>?.toAvailableOrderBookListFromEntity() = mutableListOf<AvailableOrderBook>()
    .apply {
        this@toAvailableOrderBookListFromEntity?.forEach {
            this.add(
                AvailableOrderBook(
                    book_code = it.book_code,
                    book_name = it.book_name,
                    book_format_code = it.book_format_code,
                    book_logo = it.book_logo
                )
            )
        }
    }

fun List<AvailableOrderBookEntity>?.toAvailableOrderBookBaseResponse() =
    AvailableBooksBaseResponse(
        availableBooksListData = this.toAvailableOrderBookResponseListFromEntity()
    )

fun List<AvailableOrderBookEntity>?.toAvailableOrderBookResponseListFromEntity() = mutableListOf<AvailableOrderBookResponse>()
    .apply {
        this@toAvailableOrderBookResponseListFromEntity?.forEach {
            this.add(
                AvailableOrderBookResponse(
                    book = it.book_code
                )
            )
        }
    }
