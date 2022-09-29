package com.example.cripto_challenge.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity

@Dao
interface CriptoCurrencyDao {

    @Query("SELECT * FROM available_order_book")
    fun getAllCriptoCurrencyFromDatabase(): List<AvailableOrderBookEntity>

    @Insert
    fun insertCriptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>)

    @Update
    fun updateCriptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>)

    @Query("DELETE FROM available_order_book")
    fun deleteCriptoCurrencyFromDatabase()
}