package com.example.premiummovies.domain.model.movies



data class MovieList(
    val page: Int,
    val results: List<MovieData>,
    val totalPages: Int,
    val totalResults: Int
)