package com.example.cripto_challenge.domain.use_case

class CurrencyUseCaseTest {

/*
    lateinit var systemUnderTest: CurrencyUseCase
    val repository: BitsoServiceRepository = mockk()

    val availableBookResponse: Response<AvailableBooksBaseResponse> = mockk()

    val listResponse = listOf(
        AvailableOrderBookResponse(
            "btc_mxn",
            "0.00000030000",
            "3000",
            "40000",
            "20000000",
            "10.00",
            "200000000"
        )
    )

    val listEnt =  listOf(
        AvailableOrderBookEntity(
            book_code = "btc_mxn",
            book_name = "Bitcoin",
            book_format_code = "BTC/MXN",
            book_logo = R.drawable.ic_bitcoin_logo
        )
    )

    @Before
    fun setUp() {
        systemUnderTest = CurrencyUseCase(
            repository = repository
        )
    }

    @Test
    fun `get Available Books`() = runBlocking {
        //Given
        coEvery { repository.getAvaliableBooks() } returns availableBookResponse.apply { Response.success(
            200,
            AvailableBooksBaseResponse(
                true,
                listResponse
            )
        ) }

        //When
        systemUnderTest.getAvailableBooks()


        //Then
        //coVerify(exactly = 0) { repository.getAllCriptoCurrencyFromDatabase()  }
        coVerify(atLeast = 0) {
            systemUnderTest.updateAvailableBooksDB(listEnt)
        }
    }

 */
}
