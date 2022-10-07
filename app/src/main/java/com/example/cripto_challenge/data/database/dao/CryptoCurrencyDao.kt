package com.example.cripto_challenge.data.database.dao

import androidx.room.*
import com.example.cripto_challenge.data.database.entities.*
import com.example.cripto_challenge.domain.model.OrderBook

@Dao
interface CryptoCurrencyDao {

    // Available order Book
    @Query("SELECT * FROM available_order_book_table")
    fun getAllAvailableOrderBookFromDatabase(): List<AvailableOrderBookEntity>

    @Insert
    fun insertAvailableOrderBookToDatabase(bookList: List<AvailableOrderBookEntity>)

    @Update
    fun updateAvailableOrderBookDatabase(bookList: List<AvailableOrderBookEntity>)

    @Query("DELETE FROM available_order_book_table")
    fun deleteAllAvailableOrderBookDatabase()

    // Ticker
    @Query("SELECT * FROM ticker_table WHERE book LIKE :book")
    fun getTickerFromDatabase(book: String): TickerEntity

    @Insert
    fun insertTickerToDatabase(ticker: TickerEntity)

    @Update
    fun updateTickerDatabase(ticker: TickerEntity)

    @Query("DELETE FROM ticker_table WHERE book LIKE :book")
    fun deleteTickerDatabase(book: String)

    // OrderBook Bids
    @Query("SELECT * FROM bids_table WHERE book LIKE :book")
    fun getAllOrderBookBidsFromDatabase(book: String): List<BidsEntity>

    @Insert
    fun insertOrderBookBidsToDatabase(bidsEntity: List<BidsEntity>)

    @Query("DELETE FROM bids_table WHERE book LIKE :book")
    fun deleteAllOrderBookBidsDatabase(book: String)

    // OrderBook Asks
    @Query("SELECT * FROM asks_table WHERE book LIKE :book")
    fun getAllOrderBookAsksFromDatabase(book: String): List<AsksEntity>

    @Insert
    fun insertOrderBookAsksToDatabase(asksEntityList: List<AsksEntity>)

    @Query("DELETE FROM asks_table WHERE book LIKE :book")
    fun deleteAllOrderBookAsksDatabase(book: String)

    // Order Book Open Orders
    @Transaction
    fun getOrderBookFromDatabase(book: String): OrderBook {
        val bids = getAllOrderBookBidsFromDatabase(book)
        val asks = getAllOrderBookAsksFromDatabase(book)
        return OrderBook(
            book = book,
            bids = bids.map { it.toBidsOpenOrderFromEntity() },
            asks = asks.map { it.toAsksOpenOrderFromEntity() }
        )
    }

    @Transaction
    fun insertOrderBookOpenOrdersFromDatabase(bidsEntityList: List<BidsEntity>, asksEntityList: List<AsksEntity>) {
        insertOrderBookBidsToDatabase(bidsEntityList)
        insertOrderBookAsksToDatabase(asksEntityList)
    }

    @Transaction
    fun deleteOrderBookOpenOrdersFromDatabase(book: String) {
        deleteAllOrderBookBidsDatabase(book)
        deleteAllOrderBookAsksDatabase(book)
    }
}
