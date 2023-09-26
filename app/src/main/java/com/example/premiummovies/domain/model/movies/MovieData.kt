package com.example.premiummovies.domain.model.movies


import com.google.gson.annotations.SerializedName

data class MovieData(

    val backdropPath: String?,
    val genreIds: List<Int>,
    val id: Int,
    val originalTitle: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,

    )