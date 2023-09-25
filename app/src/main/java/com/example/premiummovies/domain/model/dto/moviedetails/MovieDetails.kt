package com.example.premiummovies.domain.model.dto.moviedetails


import com.example.premiummovies.domain.model.dto.genre.GenreData

data class MovieDetails(

    val backdropPath: String,
    val budget: Int,
    val genres: List<GenreData>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
)