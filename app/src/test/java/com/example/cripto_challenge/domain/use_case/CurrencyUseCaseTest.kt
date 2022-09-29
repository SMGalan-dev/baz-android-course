package com.example.cripto_challenge.domain.use_case

import com.example.cripto_challenge.data.repository.BitsoServiceRepository
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class CurrencyUseCaseTest {

    lateinit var systemUnderTest: CurrencyUseCase
    val repository: BitsoServiceRepository = mockk()

    @Before
    fun setUp() {
        systemUnderTest = CurrencyUseCase(
            repository = repository
        )
    }

    @Test
    fun `get Available Books`() {
    }
}