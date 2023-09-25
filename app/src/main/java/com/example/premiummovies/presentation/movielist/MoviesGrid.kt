package com.example.premiummovies.presentation.movielist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MoviesGrid(state: MovieListingsState) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(state.movies.size) { i ->
            val movie = state.movies[i]
            MovieItem(
                movie = movie
            ) {

            }
            /* if (i < state.companies.size) {
                 Divider(
                     modifier = Modifier.padding(
                         horizontal = 16.dp
                     )
                 )
             }*/
        }
    }
}