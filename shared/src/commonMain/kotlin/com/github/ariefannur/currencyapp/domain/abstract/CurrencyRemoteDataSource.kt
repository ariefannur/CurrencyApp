package com.github.ariefannur.currencyapp.domain.abstract

interface CurrencyRemoteDataSource {
    suspend fun requestCurrency(): HashMap<String, Double>
}