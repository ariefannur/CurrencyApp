package com.github.ariefannur.currencyapp.data.di

import com.github.ariefannur.currencyapp.data.currency.CurrencyLocalDataSourceImpl
import com.github.ariefannur.currencyapp.data.currency.CurrencyRemoteDataSourceImpl
import com.github.ariefannur.currencyapp.data.currency.CurrencyRepositoryImpl
import com.github.ariefannur.currencyapp.data.db.Database
import com.github.ariefannur.currencyapp.domain.abstract.CurrencyLocalDataSource
import com.github.ariefannur.currencyapp.domain.abstract.CurrencyRemoteDataSource
import com.github.ariefannur.currencyapp.domain.abstract.CurrencyRepository
import com.github.ariefannur.currencyapp.domain.usecase.ConvertCurrency
import com.github.ariefannur.currencyapp.platformModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = true, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs), platformModule())
    }

// called by iOS etc
fun initKoin() = initKoin(enableNetworkLogs = true) {}

fun commonModule(enableNetworkLogs: Boolean): Module = module {

    single { createJson() }
    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob() ) }
    single { Database(get()) }

    single<CurrencyRemoteDataSource> { CurrencyRemoteDataSourceImpl(get()) }
    single<CurrencyLocalDataSource> { CurrencyLocalDataSourceImpl(get()) }
    single<CurrencyRepository> { CurrencyRepositoryImpl(get(), get()) }

    single<ConvertCurrency> {  ConvertCurrency(get()) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean) = HttpClient(httpClientEngine) {
    install(ContentNegotiation) {
        json(json)
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
    }
}