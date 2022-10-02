package com.example.cripto_challenge.data.database.data_source

import com.example.cripto_challenge.data.database.dao.CryptoCurrencyDao
import com.example.cripto_challenge.data.database.entities.AsksEntity
import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity
import com.example.cripto_challenge.data.database.entities.BidsEntity
import com.example.cripto_challenge.data.database.entities.TickerEntity
import javax.inject.Inject

class CryptoCurrencyLocalDataSource @Inject constructor(
    private val localDB: CryptoCurrencyDao
) {

    /** Available Books **/
    fun getAllAvailableOrderBookFromDatabase(): List<AvailableOrderBookEntity> =
        localDB.getAllAvailableOrderBookFromDatabase()

    fun insertAvailableOrderBookToDatabase(bookList: List<AvailableOrderBookEntity>) =
        localDB.insertAvailableOrderBookToDatabase(bookList)

    fun updateAvailableOrderBookDatabase(bookList: List<AvailableOrderBookEntity>) =
        localDB.updateAvailableOrderBookDatabase(bookList)

    /** Ticker **/
    fun getTickerFromDatabase(book: String): TickerEntity =
        localDB.getTickerFromDatabase(book)

    fun insertTickerToDatabase(ticker: TickerEntity) =
        localDB.insertTickerToDatabase(ticker)

    fun updateTickerDatabase(ticker: TickerEntity) =
        localDB.updateTickerDatabase(ticker)

    fun deleteTickerDatabase(book: String) =
        localDB.deleteTickerDatabase(book)

    /** Open orders **/
    fun getOrderBookfromDatabase(book:String) =
        localDB.getOrderBookFromDatabase(book)

    fun insertOpenOrdersToDatabase(bidsEntityList: List<BidsEntity>,asksEntityList: List<AsksEntity>) =
        localDB.insertOrderBookOpenOrdersFromDatabase(bidsEntityList, asksEntityList)

    fun deleteOpenOrdersFromDatabase(book: String) =
        localDB.deleteOrderBookOpenOrdersFromDatabase(book)

}