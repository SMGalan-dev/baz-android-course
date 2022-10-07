package com.example.cripto_challenge.data.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cripto_challenge.data.database.data_source.CryptoCurrencyLocalDataSource
import com.example.cripto_challenge.data.database.entities.toAsksEntityList
import com.example.cripto_challenge.data.database.entities.toAvailableOrderBookEntityList
import com.example.cripto_challenge.data.database.entities.toBidsEntityList
import com.example.cripto_challenge.data.remote.data_source.CryptoCurrencyNetworkDataSource
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.model.OpenOrder
import com.example.cripto_challenge.domain.model.OrderBook
import com.example.cripto_challenge.listAvailableOrderBooks
import com.example.cripto_challenge.listAvailableOrderBooksEntities
import com.example.cripto_challenge.openOrder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CryptoCurrencyRepositoryImpTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private val remoteDataSource: CryptoCurrencyNetworkDataSource = mockk()
    private val localDataSource: CryptoCurrencyLocalDataSource = mockk()
    private var context: Context = mockk()

    private lateinit var systemUnderTest: CryptoCurrencyRepositoryImp

    @Before
    fun onBefore() {
        systemUnderTest = CryptoCurrencyRepositoryImp(
            context,
            remoteDataSource,
            localDataSource
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `update available books to database function call get all available data`()  = runBlocking {
        //Given
        val list = emptyList<AvailableOrderBook>()

        //When
        systemUnderTest.updateAvailableOrderBookDatabase(list)

        //Then
        coVerify(exactly = 1) {
            localDataSource.getAllAvailableOrderBookFromDatabase()
        }
    }

    @Test
    fun `insert available books to database function when empty`()  = runBlocking {
        //Given
        every { localDataSource.getAllAvailableOrderBookFromDatabase() } returns emptyList()

        //When
        systemUnderTest.updateAvailableOrderBookDatabase(listAvailableOrderBooks)

        //Then
        verify(exactly = 1) {
            localDataSource.insertAvailableOrderBookToDatabase(listAvailableOrderBooks.toAvailableOrderBookEntityList())
        }
    }

    @Test
    fun `No insert available books to database function when has stored data`()  = runBlocking {
        //Given
        every { localDataSource.getAllAvailableOrderBookFromDatabase() } returns listAvailableOrderBooksEntities

        //When
        systemUnderTest.updateAvailableOrderBookDatabase(listAvailableOrderBooks)

        //Then
        verify(exactly = 0) {
            localDataSource.insertAvailableOrderBookToDatabase(listAvailableOrderBooksEntities)
        }
    }

    @Test
    fun `update available books to database function when has stored data`()  = runBlocking {
        //Given
        every { localDataSource.getAllAvailableOrderBookFromDatabase() } returns listAvailableOrderBooksEntities

        //When
        systemUnderTest.updateAvailableOrderBookDatabase(listAvailableOrderBooks)

        //Then
        verify(exactly = 1) {
            localDataSource.updateAvailableOrderBookDatabase(listAvailableOrderBooksEntities)
        }
    }

    @Test
    fun `delete  Order books to database functions when called`()  = runBlocking {
        //Given
        val book = "btc_mxn"
        val orderBookEmpty = OrderBook(book, emptyList(), emptyList())

        //When
        systemUnderTest.updateOrderBookDatabase(book, orderBookEmpty)

        //Then
        verify(exactly = 1) {
            localDataSource.deleteOpenOrdersFromDatabase(book)
        }
    }

    @Test
    fun `insert and insert Order books to database functions when called and empty`()  = runBlocking {
        //Given
        val book = "btc_mxn"
        val orderBookEmpty = OrderBook(book, emptyList(), emptyList())

        //When
        systemUnderTest.updateOrderBookDatabase(book, orderBookEmpty)

        //Then
        verify(exactly = 1) {
            localDataSource.insertOpenOrdersToDatabase(orderBookEmpty?.bids.toBidsEntityList(), orderBookEmpty?.asks.toAsksEntityList())
        }
    }

    @Test
    fun `insert and insert Order books to database functions when called and not empty`()  = runBlocking {
        //Given
        val book = "btc_mxn"
        val list = listOf<OpenOrder>(openOrder)
        val orderBookEmpty = OrderBook(book, list, list)
        coEvery { localDataSource.deleteOpenOrdersFromDatabase(book) } returns Unit

        //When
        systemUnderTest.updateOrderBookDatabase(book, orderBookEmpty)

        //Then
        verify(exactly = 1) {
            localDataSource.insertOpenOrdersToDatabase(orderBookEmpty?.bids.toBidsEntityList(), orderBookEmpty?.asks.toAsksEntityList())
        }
    }

}