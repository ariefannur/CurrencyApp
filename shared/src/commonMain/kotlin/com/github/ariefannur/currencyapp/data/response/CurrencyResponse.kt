package com.github.ariefannur.currencyapp.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse (
    @SerialName("timestamp")
    val timestamp: Long?,
    @SerialName("base")
    val base: String?,
    @SerialName("rates")
    val rates: HashMap<String, Double>?
)