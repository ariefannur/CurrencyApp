package com.github.ariefannur.currencyapp.domain.utils

import io.ktor.util.date.getTimeMillis
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Throttling {
    private const val TIMER = 30000
    private var lastCallApi: Long = 0L

    fun save() { lastCallApi = currentTime() }

    @OptIn(ExperimentalStdlibApi::class)
    @VisibleForTesting
    fun reset() { lastCallApi = 0L }

    private fun currentTime(): Long = getTimeMillis()

    fun isAllowToCallApi(): Boolean {
        return lastCallApi + TIMER < currentTime() || lastCallApi == 0L
    }
}