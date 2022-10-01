package com.example.cripto_challenge.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity

@Dao
interface CryptoCurrencyDao {

    @Query("SELECT * FROM available_order_book_table")
    fun getAllCryptoCurrencyFromDatabase(): List<AvailableOrderBookEntity>

    @Insert
    fun insertCryptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>)

    @Update
    fun updateCryptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>)

    @Query("DELETE FROM available_order_book_table")
    fun deleteCriptoCurrencyFromDatabase()
}