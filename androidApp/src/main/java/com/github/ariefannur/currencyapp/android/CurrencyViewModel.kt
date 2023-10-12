package com.github.ariefannur.currencyapp.android

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.ariefannur.currencyapp.domain.base.DataState
import com.github.ariefannur.currencyapp.domain.usecase.ConvertCurrency
import com.github.ariefannur.currencyapp.model.Currency

class CurrencyViewModel(
    private val convertCurrency: ConvertCurrency
): ViewModel() {
    
    var data = mutableStateOf<List<Currency>>(listOf())
    
    fun convert() {
        convertCurrency(Currency("IDR", 1000.00)) {
            when(it) {
                is DataState.Loading -> {}
                is DataState.Success -> {
                   data.value = it.result
                }
                else -> {}
            }
        }
    }
}