package com.github.ariefannur.currencyapp.domain.abstract

import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun requestCurrency(): Flow<HashMap<String, Double>>
}