package com.github.ariefannur.currencyapp.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.ariefannur.currencyapp.domain.usecase.ConvertCurrency
import com.github.ariefannur.currencyapp.model.Currency
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val convertCurrency: ConvertCurrency by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text("Hallo")
                }
            }
        }

        convertCurrency(Currency("IDR", 1000.0)) {
            Log.d("AF", "data $it")
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
