package com.example.cripto_challenge.config

import android.app.Application
import androidx.room.Room
import com.example.cripto_challenge.data.database.CriptoCurrencyDataBase

class InitApplication:Application() {
    companion object{
        lateinit var criptoCurrencyDB: CriptoCurrencyDataBase
    }

    override fun onCreate() {
        super.onCreate()
        criptoCurrencyDB = Room.databaseBuilder(
            this,
            CriptoCurrencyDataBase::class.java,
            "criptoCurrencyDB"
        ).allowMainThreadQueries()
         .build()
    }
}