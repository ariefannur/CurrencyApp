package com.github.ariefannur.currencyapp.android.view

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.github.ariefannur.currencyapp.android.R
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun CurrencyView(
    bigScreen: Boolean = false,
    openClick: (Boolean) -> Unit,
    onChange: (String) -> Unit,
    selected: TextFieldValue = TextFieldValue("USD")
    ) {
    val open by remember { mutableStateOf(false) }
    val interactionSource = remember {
        object : MutableInteractionSource {
            override val interactions = MutableSharedFlow<Interaction>(
                extraBufferCapacity = 16,
                onBufferOverflow = BufferOverflow.DROP_OLDEST,
            )

            override suspend fun emit(interaction: Interaction) {
                when (interaction) {
                    is PressInteraction.Release -> {
                        openClick.invoke(!open)
                    }
                }

                interactions.emit(interaction)
            }

            override fun tryEmit(interaction: Interaction): Boolean {
                return interactions.tryEmit(interaction)
            }
        }
    }
    val modifier = if (bigScreen) Modifier.padding(16.dp) else Modifier.fillMaxWidth().padding(horizontal = 16.dp)

    OutlinedTextField(
        modifier = modifier,
        value = selected,
        onValueChange = {
           onChange.invoke(it.text)
        },
        label = {
            Text(stringResource(R.string.choose_currency))
        },
        placeholder = {
            Text("USD")
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Localized description"
            )
        },
        readOnly = true,
        interactionSource = interactionSource
    )

}