package com.github.ariefannur.currencyapp.data.currency

import com.github.ariefannur.currencyapp.domain.abstract.CurrencyLocalDataSource
import com.github.ariefannur.currencyapp.domain.abstract.CurrencyRemoteDataSource
import com.github.ariefannur.currencyapp.domain.abstract.CurrencyRepository
import com.github.ariefannur.currencyapp.domain.utils.Throttling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrencyRepositoryImpl(
    private val remote: CurrencyRemoteDataSource,
    private val local: CurrencyLocalDataSource
): CurrencyRepository {
    override suspend fun requestCurrency(): Flow<HashMap<String, Double>> = flow {
        with(local.getCurrency()) {
            if (!this.isNullOrEmpty()) {
                emit(this)
            }
            // Throttling flag
            if (Throttling.isAllowToCallApi() || this.isNullOrEmpty()) {
                Throttling.save()
                val remote = remote.requestCurrency()
                if (remote.isNotEmpty()) {
                    kotlin.runCatching { local.saveCurrency(remote) }
                    emit(remote)
                }
            }
        }
    }
}