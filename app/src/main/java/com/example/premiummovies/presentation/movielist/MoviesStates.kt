package com.example.premiummovies.presentation.movielist

import com.example.premiummovies.domain.model.genre.GenreData
import com.example.premiummovies.domain.model.genre.GenreList
import com.example.premiummovies.domain.model.moviedetails.MovieDetails
import com.example.premiummovies.domain.model.movies.MovieData

data class MovieListingsState(
    val movies: MutableList<MovieData> = mutableListOf(),
    val genres: GenreList? = null,
    var selectedGenre: GenreData? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    var searchQuery: String = "",
    var pageNo: Int = 0
)


data class MovieDetailState(
    val movie: MovieDetails? = null,
    val isLoading: Boolean = false,
    val genres: String = "",
    val languages: String = "",
)