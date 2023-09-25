package com.example.premiummovies.presentation.movielist

import com.example.premiummovies.domain.model.dto.movies.MovieData

data class MovieListingsState(
    val movies: List<MovieData> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val pageNo: Int = 1
)
