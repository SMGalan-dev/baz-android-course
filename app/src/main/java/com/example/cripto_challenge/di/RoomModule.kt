package com.example.cripto_challenge.di

import android.content.Context
import androidx.room.Room
import com.example.cripto_challenge.data.database.CriptoCurrencyDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val CRYPTOCURRENCY_DATABASE_NAME = "criptoCurrency_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CriptoCurrencyDataBase::class.java, CRYPTOCURRENCY_DATABASE_NAME)
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideCryptoCurrencyDao(db: CriptoCurrencyDataBase) = db.getCriptoCurrencyDao()
}