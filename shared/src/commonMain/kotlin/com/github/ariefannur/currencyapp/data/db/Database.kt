package com.github.ariefannur.currencyapp.data.db

import com.github.ariefannur.currencyapp.DatabaseDriverFactory
import com.github.ariefannur.currencyapp.db.CurrencyDatabase

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    val db = CurrencyDatabase(databaseDriverFactory.createDriver())
    val dbQueries = db.currencyQueries
}