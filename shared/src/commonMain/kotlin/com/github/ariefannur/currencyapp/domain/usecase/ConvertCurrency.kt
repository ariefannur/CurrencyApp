package com.github.ariefannur.currencyapp.domain.usecase

import com.github.ariefannur.currencyapp.domain.abstract.CurrencyRepository
import com.github.ariefannur.currencyapp.domain.base.UseCase
import com.github.ariefannur.currencyapp.domain.utils.toCurrency
import com.github.ariefannur.currencyapp.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConvertCurrency(
    private val repository: CurrencyRepository
): UseCase<List<Currency>, Currency>() {
    override suspend fun run(params: Currency): Flow<List<Currency>> = flow {
        repository.requestCurrency().collect {
            emit(it.toCurrency(params.rate, params.code))
        }
    }
}