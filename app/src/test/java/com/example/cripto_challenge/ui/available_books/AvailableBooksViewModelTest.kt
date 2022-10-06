package com.example.cripto_challenge.ui.available_books

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cripto_challenge.common.RequestState
import com.example.cripto_challenge.domain.model.AvailableOrderBook
import com.example.cripto_challenge.domain.use_case.GetAvailableBooksRxJavaUseCase
import com.example.cripto_challenge.domain.use_case.GetAvailableBooksUseCase
import com.example.cripto_challenge.domain.use_case.UpdateAvailableBooksDBUseCase
import com.example.cripto_challenge.listAvailableOrderBooks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

import org.junit.Test

@ExperimentalCoroutinesApi
class AvailableBooksViewModelTest {

    private lateinit var systemUnderTest: AvailableBooksViewModel
    private val getAvailableBooksUseCase: GetAvailableBooksUseCase = mockk()
    private val getAvailableBooksRxJavaUseCase: GetAvailableBooksRxJavaUseCase = mockk()
    private val updateAvailableBooksRxJavaUseCase: UpdateAvailableBooksDBUseCase = mockk()

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        systemUnderTest = AvailableBooksViewModel(
            availableBooksUseCase = getAvailableBooksUseCase,
            availableBooksRxJavaUseCase = getAvailableBooksRxJavaUseCase,
            updateAvailableBooksDBUseCase = updateAvailableBooksRxJavaUseCase,
            defaultScheduler = Schedulers.trampoline()
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get Available Books Success Response with not empty list`() = runTest {
        //Given
        coEvery { getAvailableBooksUseCase.invoke() } returns flow {
            RequestState.Success<List<AvailableOrderBook>>(listAvailableOrderBooks)
        }

        //When
        systemUnderTest.getAvailableBooks(){}

        //Then
        systemUnderTest.availableOrderBookList.value?.isNullOrEmpty()?.let {
            assert(
                !it
            )
        }
    }

    @Test
    fun `function getAvailableBooksUseCaseinvoke() is called once`() = runTest {
        //Given
        coEvery { getAvailableBooksUseCase.invoke() } returns flow {
            RequestState.Success<List<AvailableOrderBook>>(emptyList())
        }

        //When
        systemUnderTest.getAvailableBooks(){}

        //Then
        coVerify(exactly = 1) {
                getAvailableBooksUseCase.invoke()
        }
    }

}
