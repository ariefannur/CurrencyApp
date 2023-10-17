package com.github.ariefannur.currencyapp.android.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.ariefannur.currencyapp.DecimalFormat
import com.github.ariefannur.currencyapp.model.Currency

@Composable
fun GridConversionView(listCurrency: List<Currency>) {
    LazyVerticalGrid (
        columns = GridCells.Adaptive(128.dp),

        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),

        content = {
            items(listCurrency) { currency ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth().padding(8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column {
                        Text(
                            modifier = Modifier.padding(bottom = 0.dp, start = 16.dp, end = 16.dp, top = 16.dp),
                            text = currency.code,
                            style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = DecimalFormat().format(currency.rate),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 0.dp)
                        )
                    }
                }
            }
        }
    )
}