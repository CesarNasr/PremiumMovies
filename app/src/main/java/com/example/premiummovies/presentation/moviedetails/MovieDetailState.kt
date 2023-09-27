package com.example.premiummovies.presentation.moviedetails

import com.example.premiummovies.domain.model.moviedetails.MovieDetails


data class MovieDetailState(
    val movie: MovieDetails? = null,
    val isLoading: Boolean = false,
    val genres: String = "",
    val languages: String = "",
)