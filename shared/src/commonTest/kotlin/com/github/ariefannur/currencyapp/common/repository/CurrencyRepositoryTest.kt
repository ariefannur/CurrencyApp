package com.github.ariefannur.currencyapp.common.repository

import com.github.ariefannur.currencyapp.data.currency.CurrencyRepositoryImpl
import com.github.ariefannur.currencyapp.domain.abstract.CurrencyLocalDataSource
import com.github.ariefannur.currencyapp.domain.abstract.CurrencyRemoteDataSource
import com.github.ariefannur.currencyapp.domain.utils.Throttling
import com.github.ariefannur.currencyapp.runBlockingTest
import kotlinx.coroutines.flow.toList
import org.kodein.mock.Mock
import org.kodein.mock.tests.TestsWithMocks
import kotlin.test.Test
import kotlin.test.assertTrue

class CurrencyRepositoryTest : TestsWithMocks() {


    @Mock lateinit var local: CurrencyLocalDataSource
    @Mock lateinit var remote: CurrencyRemoteDataSource

    override fun setUpMocks() = injectMocks(mocker)

    private val repository by withMocks { CurrencyRepositoryImpl(remote, local) }
    private val dummyCurrencies = hashMapOf<String, Double>(
        "IDR" to 100.00,
        "USD" to 130.00,
        "YPN" to 120.00,
        "AUD" to 140.00,
    )

    @Test
    fun `check repository got empty from local and empty from remote`() = runBlockingTest {
        everySuspending { local.getCurrency() } returns hashMapOf()
        everySuspending { remote.requestCurrency() } returns hashMapOf()

        val result = repository.requestCurrency().toList()
        assertTrue {  result.isEmpty() }
    }

    @Test
    fun `check repository got data from local and empty from remote`() = runBlockingTest {
        everySuspending { local.getCurrency() } returns dummyCurrencies
        everySuspending { remote.requestCurrency() } returns hashMapOf()

        val result = repository.requestCurrency().toList()
        assertTrue { result.first() == dummyCurrencies }
    }

    @Test
    fun `check repository got data from local and got data from remote`() = runBlockingTest {

        everySuspending { local.getCurrency() } returns dummyCurrencies
        everySuspending { remote.requestCurrency() } returns dummyCurrencies
        Throttling.reset()
        val result = repository.requestCurrency().toList()
        assertTrue { result.first() == dummyCurrencies && result[1] == dummyCurrencies}
    }

    @Test
    fun `check repository got data from local and got throttling checker`() = runBlockingTest {
        everySuspending { local.getCurrency() } returns dummyCurrencies
        everySuspending { remote.requestCurrency() } returns dummyCurrencies
        Throttling.save()

        val result = repository.requestCurrency().toList()
        assertTrue { result.first() == dummyCurrencies }
    }
}