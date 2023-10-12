package com.github.ariefannur.currencyapp.data.currency

import com.github.ariefannur.currencyapp.data.db.Database
import com.github.ariefannur.currencyapp.domain.abstract.CurrencyLocalDataSource

class CurrencyLocalDataSourceImpl(
    private val db: Database
): CurrencyLocalDataSource {
    override suspend fun getCurrency(): HashMap<String, Double> {
         val map = hashMapOf<String, Double>()
          db.dbQueries.selectAll().executeAsList().forEach {
              map[it.code] = it.value_ ?: 0.0
          }
        return map
    }

    private fun mapCurrency(code: String, value: Double?): HashMap<String, Double> {
        return hashMapOf(code to (value ?: 0.0))
    }

    override suspend fun saveCurrency(datas: HashMap<String, Double>) {
        for (data in datas) {
            db.dbQueries.insertCurrency(data.key, data.value)
        }
    }

    override suspend fun getRate(key: String): Pair<String, Double> {
        return db.dbQueries.selectOne(key, ::mapRate).executeAsOne()
    }

    private fun mapRate(code: String, value: Double?): Pair<String, Double> {
        return code to (value ?: 0.0)
    }
}