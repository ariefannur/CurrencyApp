package com.github.ariefannur.currencyapp.domain.utils

import kotlin.native.concurrent.ThreadLocal
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

@ThreadLocal
object Throttling {
    private val TIMER = 3.seconds
    private var lastCallApi: Duration = 0.milliseconds

    fun save() { lastCallApi = currentTime() }

    private fun currentTime(): Duration = TimeSource.Monotonic.markNow().elapsedNow()

    fun isAllowToCallApi(): Boolean {
        println("AF: last -> $lastCallApi current -> ${currentTime()}")
        return lastCallApi + TIMER < currentTime() || lastCallApi == 0.seconds
    }
}