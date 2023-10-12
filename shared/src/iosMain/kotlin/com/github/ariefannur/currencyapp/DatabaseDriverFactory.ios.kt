package com.github.ariefannur.currencyapp

import com.github.ariefannur.currencyapp.db.CurrencyDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(CurrencyDatabase.Schema, "crypto.db")
    }
}