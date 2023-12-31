package com.example.premiummovies.domain.model.moviedetails


import com.example.premiummovies.domain.model.genre.GenreData

data class MovieDetails(

    val backdropPath: String?,
    val budget: Int,
    val genres: List<GenreData>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val homePage: String
)