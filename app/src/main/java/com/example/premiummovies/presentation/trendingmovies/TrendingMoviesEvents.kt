package com.example.premiummovies.presentation.trendingmovies

sealed class TrendingMoviesEvents {
    object FilterMovies: TrendingMoviesEvents()
    data class LoadMovies(val loadGenres: Boolean = false): TrendingMoviesEvents()
}
