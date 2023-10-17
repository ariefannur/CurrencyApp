package com.github.ariefannur.currencyapp.android

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.ariefannur.currencyapp.domain.base.DataState
import com.github.ariefannur.currencyapp.domain.usecase.ConvertCurrency
import com.github.ariefannur.currencyapp.model.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CurrencyViewModel(
    private val convertCurrency: ConvertCurrency
): ViewModel() {
    
    private val _listData = MutableStateFlow<List<Currency>>(listOf())
    val gridState: StateFlow<List<Currency>> = _listData.asStateFlow()

    private val _listCurrencyCode = MutableStateFlow<List<String>>(listOf())
    val listCurrencyCode = _listCurrencyCode.asStateFlow()

    val inputCurrencyCode = MutableStateFlow("USD")
    val currencyCodeState: StateFlow<String> = inputCurrencyCode.asStateFlow()

    val inputCurrencyNominal = MutableStateFlow(1.0)

    val internetConnection = MutableStateFlow(false)
    val internetState : StateFlow<Boolean> = internetConnection.asStateFlow()


    fun convert() {
        convertCurrency(Currency(inputCurrencyCode.value, inputCurrencyNominal.value)) {
            when(it) {
                is DataState.Loading -> {}
                is DataState.Success -> {
                   _listData.value = it.result
                   _listCurrencyCode.value = it.result.map { it.code }
                }
                is DataState.Failure -> {
                    if (it.errorCode == 404) internetConnection.value = true
                }
            }
        }
    }

}