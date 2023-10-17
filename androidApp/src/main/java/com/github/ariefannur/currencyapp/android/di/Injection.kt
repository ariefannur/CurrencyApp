package com.github.ariefannur.currencyapp.android.di

import com.github.ariefannur.currencyapp.android.CurrencyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModel { CurrencyViewModel(get())}
    viewModelOf(::CurrencyViewModel)
}
