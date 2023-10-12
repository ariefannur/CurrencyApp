package com.github.ariefannur.currencyapp.db

import com.github.ariefannur.currencyapp.db.shared.newInstance
import com.github.ariefannur.currencyapp.db.shared.schema
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver

public interface CurrencyDatabase : Transacter {
  public val currencyQueries: CurrencyQueries

  public companion object {
    public val Schema: SqlDriver.Schema
      get() = CurrencyDatabase::class.schema

    public operator fun invoke(driver: SqlDriver): CurrencyDatabase =
        CurrencyDatabase::class.newInstance(driver)
  }
}
