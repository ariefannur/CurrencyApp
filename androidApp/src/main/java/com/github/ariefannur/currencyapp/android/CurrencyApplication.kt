package com.github.ariefannur.currencyapp.android

import android.app.Application
import com.github.ariefannur.currencyapp.data.di.initKoin
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class CurrencyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(true) {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@CurrencyApplication)
        }
    }
}