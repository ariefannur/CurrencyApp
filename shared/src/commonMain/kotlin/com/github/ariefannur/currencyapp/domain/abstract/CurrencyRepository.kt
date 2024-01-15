package com.github.ariefannur.currencyapp.domain.abstract

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    @NativeCoroutines
    suspend fun requestCurrency(): Flow<HashMap<String, Double>>
}