package com.github.ariefannur.currencyapp.db.shared

import com.github.ariefannur.currencyapp.db.Currency
import com.github.ariefannur.currencyapp.db.CurrencyDatabase
import com.github.ariefannur.currencyapp.db.CurrencyQueries
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.`internal`.copyOnWriteList
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.collections.MutableList
import kotlin.reflect.KClass

internal val KClass<CurrencyDatabase>.schema: SqlDriver.Schema
  get() = CurrencyDatabaseImpl.Schema

internal fun KClass<CurrencyDatabase>.newInstance(driver: SqlDriver): CurrencyDatabase =
    CurrencyDatabaseImpl(driver)

private class CurrencyDatabaseImpl(
  driver: SqlDriver
) : TransacterImpl(driver), CurrencyDatabase {
  public override val currencyQueries: CurrencyQueriesImpl = CurrencyQueriesImpl(this, driver)

  public object Schema : SqlDriver.Schema {
    public override val version: Int
      get() = 1

    public override fun create(driver: SqlDriver): Unit {
      driver.execute(null, """
          |CREATE TABLE Currency (
          |code TEXT NOT NULL PRIMARY KEY,
          |value REAL
          |)
          """.trimMargin(), 0)
    }

    public override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ): Unit {
    }
  }
}

private class CurrencyQueriesImpl(
  private val database: CurrencyDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), CurrencyQueries {
  internal val selectOne: MutableList<Query<*>> = copyOnWriteList()

  internal val selectAll: MutableList<Query<*>> = copyOnWriteList()

  public override fun <T : Any> selectOne(code: String, mapper: (code: String,
      value_: Double?) -> T): Query<T> = SelectOneQuery(code) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getDouble(1)
    )
  }

  public override fun selectOne(code: String): Query<Currency> = selectOne(code) { code_, value_ ->
    Currency(
      code_,
      value_
    )
  }

  public override fun <T : Any> selectAll(mapper: (code: String, value_: Double?) -> T): Query<T> =
      Query(-1521621581, selectAll, driver, "Currency.sq", "selectAll", "SELECT * FROM Currency") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getDouble(1)
    )
  }

  public override fun selectAll(): Query<Currency> = selectAll { code, value_ ->
    Currency(
      code,
      value_
    )
  }

  public override fun insertCurrency(code: String, value_: Double?): Unit {
    driver.execute(1823784956, """INSERT OR REPLACE INTO Currency(code, value) VALUES(?, ?)""", 2) {
      bindString(1, code)
      bindDouble(2, value_)
    }
    notifyQueries(1823784956, {database.currencyQueries.selectAll +
        database.currencyQueries.selectOne})
  }

  private inner class SelectOneQuery<out T : Any>(
    public val code: String,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectOne, mapper) {
    public override fun execute(): SqlCursor = driver.executeQuery(-1521608072,
        """SELECT * FROM Currency WHERE (code = ?)""", 1) {
      bindString(1, code)
    }

    public override fun toString(): String = "Currency.sq:selectOne"
  }
}
