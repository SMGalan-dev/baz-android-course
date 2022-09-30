package com.example.cripto_challenge.data.database.data_source

import com.example.cripto_challenge.data.database.dao.CryptoCurrencyDao
import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity

class CryptoCurrencyLocalDataSource (
    private val localDB: CryptoCurrencyDao
) {

    suspend fun getAllCryptoCurrencyFromDatabase(): List<AvailableOrderBookEntity> =
        localDB.getAllCryptoCurrencyFromDatabase()

    suspend fun insertCryptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>) =
        localDB.insertCryptoCurrencyToDatabase(bookList)

    suspend fun updateCryptoCurrencyToDatabase(bookList: List<AvailableOrderBookEntity>) =
        localDB.updateCryptoCurrencyToDatabase(bookList)

}