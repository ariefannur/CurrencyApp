package com.github.ariefannur.currencyapp.android

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.ariefannur.currencyapp.android.theme.AppTheme
import com.github.ariefannur.currencyapp.android.view.AppTopBar
import com.github.ariefannur.currencyapp.android.view.BottomSheetView
import com.github.ariefannur.currencyapp.android.view.CurrencyView
import com.github.ariefannur.currencyapp.android.view.GridConversionView
import com.github.ariefannur.currencyapp.android.view.NominalView
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen() {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val viewModel: CurrencyViewModel = getViewModel<CurrencyViewModel>()
    viewModel.convert()

    val gridData by viewModel.gridState.collectAsStateWithLifecycle()
    val currencyCode by viewModel.currencyCodeState.collectAsStateWithLifecycle()
    val listCurrencyCode by viewModel.listCurrencyCode.collectAsStateWithLifecycle()
    var isDarkMode by remember { mutableStateOf(false) }
    val internetState by viewModel.internetState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    AppTheme(useDarkTheme = isDarkMode) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            topBar = {
                AppTopBar(isDarkMode, onChangeMode = {
                    isDarkMode = it
                })
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                BoxWithConstraints {
                    if (maxWidth > 400.dp) {
                        Row {
                            NominalView(bigScreen = true, onChange = { nominal ->
                                viewModel.inputCurrencyNominal.value = nominal
                                viewModel.convert()
                            })
                            CurrencyView(
                                bigScreen = true,
                                openClick = {
                                    openBottomSheet = it
                                },
                                onChange = {
                                    viewModel.inputCurrencyCode.value = it
                                },
                                selected = TextFieldValue(currencyCode)
                            )
                        }
                    } else {
                        Column {
                            NominalView(onChange = { nominal ->
                                viewModel.inputCurrencyNominal.value = nominal
                                viewModel.convert()
                            })
                            CurrencyView(
                                openClick = {
                                    openBottomSheet = it
                                },
                                onChange = {
                                    viewModel.inputCurrencyCode.value = it
                                },
                                selected = TextFieldValue(currencyCode)
                            )
                        }
                    }
                }
                GridConversionView(gridData)
            }

            if (openBottomSheet) {
                BottomSheetView(selected = currencyCode, listData = listCurrencyCode, dismiss = { selected ->
                    openBottomSheet = false
                    viewModel.inputCurrencyCode.value = selected
                    viewModel.convert()
                })
            }

            if (internetState) {
                scope.launch {
                    snackBarHostState.showSnackbar("Check your internet connection")
                    viewModel.internetConnection.value = false
                }
            }
        }
    }
}