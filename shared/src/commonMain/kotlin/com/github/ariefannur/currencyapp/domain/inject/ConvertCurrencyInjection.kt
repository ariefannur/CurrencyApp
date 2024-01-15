package com.github.ariefannur.currencyapp.domain.inject

import com.github.ariefannur.currencyapp.data.currency.CurrencyRepositoryImpl
import com.github.ariefannur.currencyapp.domain.usecase.ConvertCurrency
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConvertCurrencyInjection: KoinComponent {
    private val convertCurrency : ConvertCurrency by inject()
    private val currencyRepository : CurrencyRepositoryImpl by inject()
    fun doConvert(): ConvertCurrency = convertCurrency
    fun getRepository(): CurrencyRepositoryImpl = currencyRepository
}