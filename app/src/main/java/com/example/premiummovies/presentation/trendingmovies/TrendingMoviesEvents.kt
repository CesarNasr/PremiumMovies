package com.example.premiummovies.presentation.trendingmovies

sealed class TrendingMoviesEvents {
    object LoadMovies: TrendingMoviesEvents()
    object FilterMovies: TrendingMoviesEvents()
    //data class FilterMovies(val query: String): TrendingMoviesEvents()
}
