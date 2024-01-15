package com.github.ariefannur.currencyapp.domain.usecase

import com.github.ariefannur.currencyapp.domain.abstract.CurrencyRepository
import com.github.ariefannur.currencyapp.domain.base.UseCase
import com.github.ariefannur.currencyapp.domain.utils.toCurrency
import com.github.ariefannur.currencyapp.model.Currency
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConvertCurrency(
    private val repository: CurrencyRepository
): UseCase<List<Currency>, Currency>() {
    @NativeCoroutines
    override suspend fun run(params: Currency): Flow<List<Currency>> = flow {
        if (params.code.length > 3) throw Exception("Invalid params")
        if (params.rate == 0.0) throw Exception("Invalid params")
        repository.requestCurrency().collect {
            emit(it.toCurrency(params.rate, params.code))
        }
    }


}
