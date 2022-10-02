package com.example.cripto_challenge.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity
import com.example.cripto_challenge.data.database.entities.TickerEntity

@Dao
interface CryptoCurrencyDao {

    //Available order Book
    @Query("SELECT * FROM available_order_book_table")
    fun getAllAvailableOrderBookFromDatabase(): List<AvailableOrderBookEntity>

    @Insert
    fun insertAvailableOrderBookToDatabase(bookList: List<AvailableOrderBookEntity>)

    @Update
    fun updateAvailableOrderBookDatabase(bookList: List<AvailableOrderBookEntity>)

    @Query("DELETE FROM available_order_book_table")
    fun deleteAllAvailableOrderBookDatabase()

    //Ticker
    @Query("SELECT * FROM ticker_table WHERE book LIKE :book")
    fun getTickerFromDatabase(book: String): TickerEntity

    @Insert
    fun insertTickerToDatabase(ticker: TickerEntity)

    @Update
    fun updateTickerDatabase(ticker: TickerEntity)

    @Query("DELETE FROM ticker_table WHERE book LIKE :book")
    fun deleteTickerDatabase(book: String)

}