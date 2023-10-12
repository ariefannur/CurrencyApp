package com.github.ariefannur.currencyapp.db

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Double
import kotlin.String
import kotlin.Unit

public interface CurrencyQueries : Transacter {
  public fun <T : Any> selectOne(code: String, mapper: (code: String, value_: Double?) -> T):
      Query<T>

  public fun selectOne(code: String): Query<Currency>

  public fun <T : Any> selectAll(mapper: (code: String, value_: Double?) -> T): Query<T>

  public fun selectAll(): Query<Currency>

  public fun insertCurrency(code: String, value_: Double?): Unit
}
