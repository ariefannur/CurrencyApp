package com.github.ariefannur.currencyapp.domain.abstract

interface CurrencyLocalDataSource {
    suspend fun getCurrency(): HashMap<String, Double>?
    suspend fun saveCurrency(data: HashMap<String, Double>)
    suspend fun getRate(key: String): Pair<String, Double>
}