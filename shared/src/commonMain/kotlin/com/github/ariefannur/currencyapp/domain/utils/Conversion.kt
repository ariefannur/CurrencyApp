package com.github.ariefannur.currencyapp.domain.utils

import com.github.ariefannur.currencyapp.model.Currency



fun HashMap<String, Double>.toCurrency(value: Double, code: String): List<Currency> {
    val baseCurrencyOnUSD: Double = this[code] ?: 0.0
    return this.map {
        Currency(it.key, value / baseCurrencyOnUSD)
    }
}
