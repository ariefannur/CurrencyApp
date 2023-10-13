package com.github.ariefannur.currencyapp.android

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.ariefannur.currencyapp.android.theme.AppTheme
import com.github.ariefannur.currencyapp.android.view.AppTopBar
import com.github.ariefannur.currencyapp.android.view.CurrencyView
import com.github.ariefannur.currencyapp.android.view.GridConversionView
import com.github.ariefannur.currencyapp.android.view.NominalView
import com.github.ariefannur.currencyapp.domain.usecase.ConvertCurrency
import com.github.ariefannur.currencyapp.model.Currency
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val convertCurrency: ConvertCurrency by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Scaffold(
                    topBar = {
                        AppTopBar()
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        NominalView()
                    }
                }
            }
        }

        convertCurrency(Currency("IDR", 1000.0)) {
            Log.d("AF", "data $it")
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun DefaultPreview() {
    AppTheme {
        Scaffold(
            topBar = {
                AppTopBar()
            }

        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                NominalView()
                CurrencyView()
                GridConversionView()
            }
        }
    }
}
