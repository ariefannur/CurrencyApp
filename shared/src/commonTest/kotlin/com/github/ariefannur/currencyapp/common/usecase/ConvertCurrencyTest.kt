package com.github.ariefannur.currencyapp.common.usecase

import com.github.ariefannur.currencyapp.domain.abstract.CurrencyRepository
import com.github.ariefannur.currencyapp.domain.base.DataState
import com.github.ariefannur.currencyapp.domain.usecase.ConvertCurrency
import com.github.ariefannur.currencyapp.model.Currency
import com.github.ariefannur.currencyapp.runBlockingTest
import kotlinx.coroutines.flow.flowOf
import org.kodein.mock.Mock
import org.kodein.mock.tests.TestsWithMocks
import kotlin.test.Test
import kotlin.test.assertTrue

class ConvertCurrencyTest : TestsWithMocks()  {


    override fun setUpMocks() = injectMocks(mocker)

    @Mock lateinit var repository: CurrencyRepository
    private val convertCurrency by withMocks { ConvertCurrency(repository) }

    private val dummyCurrencies = hashMapOf<String, Double>(
        "IDR" to 100.00,
        "USD" to 130.00,
        "YPN" to 120.00,
        "AUD" to 140.00,
    )

    @Test
    fun `ConvertCurrencyText invalid input param`() = runBlockingTest {

        convertCurrency(Currency("CCCC", 0.0)) {
            assertTrue { it is DataState.Failure && it.message.contains("Invalid params") }
        }
    }

    @Test
    fun `ConvertCurrencyText Check Convert`() = runBlockingTest {
        everySuspending {  repository.requestCurrency() } returns flowOf(dummyCurrencies)
        convertCurrency(Currency("USD", 1.0)) {
            println(it)
            verify { it is DataState.Loading}
            verify { it is DataState.Success && it.result == dummyCurrencies}
        }
    }

}