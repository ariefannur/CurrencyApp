package com.github.ariefannur.currencyapp

import android.content.Context
import com.github.ariefannur.currencyapp.db.CurrencyDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(CurrencyDatabase.Schema, context, "currency.db")
    }

}