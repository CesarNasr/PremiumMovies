package com.example.premiummovies.presentation.trendingmovies

import com.example.premiummovies.domain.model.genre.GenreData
import com.example.premiummovies.domain.model.genre.GenreList
import com.example.premiummovies.domain.model.movies.MovieData

data class MoviesState(
    val movies: MutableList<MovieData> = mutableListOf(),
    val genres: GenreList? = null,
    var selectedGenre: GenreData? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    var searchQuery: String = "",
    var pageNo: Int = 0
)
