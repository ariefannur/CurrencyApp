package com.github.ariefannur.currencyapp

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module


actual fun platformModule() = module {
    single { Darwin.create() }
    single { DatabaseDriverFactory() }
}