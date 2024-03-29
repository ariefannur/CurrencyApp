package com.github.ariefannur.currencyapp.domain.utils

import com.github.ariefannur.currencyapp.model.Currency



fun HashMap<String, Double>.toCurrency(value: Double, code: String): List<Currency> {
    val baseCurrencyOnUSD: Double = this.getValue(code) ?: 0.0

    return this.map {
        val selectedCurrency: Double = this.getValue(it.key)
        val result = (value / baseCurrencyOnUSD ) * selectedCurrency
        Currency(it.key, result)
    }
}

