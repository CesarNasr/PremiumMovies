package com.example.premiummovies.presentation.movielist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var text = remember { mutableStateOf("") }

    TextField(
        value = "searchText",
        onValueChange = {}, //viewModel::onSearchTextChange },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search") }
    )

}