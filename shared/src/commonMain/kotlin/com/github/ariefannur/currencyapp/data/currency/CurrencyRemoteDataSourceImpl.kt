package com.github.ariefannur.currencyapp.data.currency

import com.github.ariefannur.currencyapp.data.LATEST
import com.github.ariefannur.currencyapp.data.response.CurrencyResponse
import com.github.ariefannur.currencyapp.domain.abstract.CurrencyRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class CurrencyRemoteDataSourceImpl(
    private val client: HttpClient
): CurrencyRemoteDataSource {
    override suspend fun requestCurrency(): HashMap<String, Double> {
        val response = client.get(LATEST)
        if (response.status == HttpStatusCode.OK) {
            return response.body<CurrencyResponse>().rates ?: hashMapOf()
        } else throw Exception(response.status.description)
    }
}