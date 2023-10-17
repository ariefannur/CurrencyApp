package com.github.ariefannur.currencyapp.android.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.github.ariefannur.currencyapp.android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetView(
    dismiss: (String) -> Unit,
    listData: List<String>,
    selected: String
    ) {
    var selectedItem = ""
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    selectedItem = selected
    var texSearch by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var mutableListData by remember { mutableStateOf(listData) }
    ModalBottomSheet(
        onDismissRequest = { dismiss.invoke(selectedItem) },
        sheetState = bottomSheetState,
        windowInsets = WindowInsets(0)
    ) {

        OutlinedTextField(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            value = texSearch,
            label = {
                Text("Search")
            },
            onValueChange = { text ->
                texSearch = text
                mutableListData = mutableListData.filter { it.contains(text.text, ignoreCase = true) }
            },
            trailingIcon = {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "", modifier = Modifier.clickable {
                    texSearch = TextFieldValue("")
                    mutableListData = listData.toMutableList()
                })
            }
        )
        LazyColumn {
            items(mutableListData) {
                ListItem(
                    modifier = Modifier.clickable {
                        selectedItem = it
                       dismiss.invoke(selectedItem)
                    },
                    headlineContent = { Text(it) },
                    trailingContent = {
                        if (selected == it)
                            Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
                    }
                )
            }
        }
        Spacer(Modifier.navigationBarsPadding())
    }
}