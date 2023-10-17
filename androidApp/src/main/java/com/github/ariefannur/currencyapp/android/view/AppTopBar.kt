package com.github.ariefannur.currencyapp.android.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.github.ariefannur.currencyapp.android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(isDarkMode: Boolean = false, onChangeMode: (Boolean) -> Unit = {}) {
    var menuExpanded by rememberSaveable { mutableStateOf(false) }
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { menuExpanded = !menuExpanded }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Localized description"
                )
            }
            DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = !menuExpanded }) {
                DropdownMenuItem(text = {
                   Text(text = stringResource(if (isDarkMode) R.string.light_mode else R.string.dark_mode))
                }, onClick = {
                    menuExpanded = !menuExpanded
                    onChangeMode.invoke(!isDarkMode)
                })
            }
        },
        title = {
            Text(text = stringResource(id = R.string.app_name))
        }
    )
}
