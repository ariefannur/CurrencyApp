package com.github.ariefannur.currencyapp.android.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.github.ariefannur.currencyapp.android.R

@Composable
fun NominalView(bigScreen: Boolean = false, onChange: (Double) -> Unit) {
    var textState by remember { mutableStateOf( TextFieldValue("1.0")) }
    val modifier = if (bigScreen) Modifier.padding(16.dp) else Modifier
        .fillMaxWidth()
        .padding(16.dp)
    OutlinedTextField(
        modifier = modifier,
        value = textState,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        onValueChange = {
            textState = it
            val nominal = try {
                if(it.text.isEmpty()) 0.0 else it.text.toDouble()
            } catch (e: Exception) {0.0}
            onChange.invoke(nominal)
        },
        label = {
            Text(stringResource(R.string.input_nominal))
        }
    )

}