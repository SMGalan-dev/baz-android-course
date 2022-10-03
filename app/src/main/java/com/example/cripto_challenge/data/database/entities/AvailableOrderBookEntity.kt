package com.example.cripto_challenge.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "available_order_book")
data class AvailableOrderBookEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "book_code") var book_code: String? = null,
    @ColumnInfo(name = "book_name") var book_name: String? = null,
    @ColumnInfo(name = "book_format_code") var book_format_code: String? = null,
    @ColumnInfo(name = "book_logo") var book_logo: Int? = null
)