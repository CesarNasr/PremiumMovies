package com.example.premiummovies.presentation.trendingmovies

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.premiummovies.domain.model.genre.GenreData


@ExperimentalMaterial3Api
@Composable
fun FilterChipGroup(itemsList: List<GenreData>, selectedGenre : GenreData?,onGenreSelected: (GenreData) -> Unit) {

    var selectedItem by remember {
        mutableStateOf(selectedGenre)
    }

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(itemsList.size) { i ->
            val item = itemsList[i]
            FilterChip(
                modifier = Modifier.padding(horizontal = 6.dp), // gap between items
                selected = (item == selectedItem),
                onClick = {
                    selectedItem = item
                    selectedItem?.let {
                        onGenreSelected(it)
                    }
                },
                label = {
                    Text(text = item.name)
                }
            )
        }
    }
}