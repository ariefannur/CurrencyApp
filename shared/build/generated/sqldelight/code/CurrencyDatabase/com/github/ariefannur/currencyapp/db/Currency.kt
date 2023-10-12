package com.github.ariefannur.currencyapp.db

import kotlin.Double
import kotlin.String

public data class Currency(
  public val code: String,
  public val value_: Double?
) {
  public override fun toString(): String = """
  |Currency [
  |  code: $code
  |  value_: $value_
  |]
  """.trimMargin()
}
