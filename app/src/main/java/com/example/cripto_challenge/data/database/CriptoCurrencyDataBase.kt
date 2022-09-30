package com.example.cripto_challenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cripto_challenge.data.database.dao.CryptoCurrencyDao
import com.example.cripto_challenge.data.database.entities.AvailableOrderBookEntity

@Database(entities = [AvailableOrderBookEntity::class], version = 1)
abstract class CriptoCurrencyDataBase: RoomDatabase() {

    abstract fun getCriptoCurrencyDao(): CryptoCurrencyDao

}