package com.example.premiummovies.data.remotedatasource.api.dto.movies


import com.google.gson.annotations.SerializedName

data class MovieDataDto(

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("id")
    val id: Int,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("title")
    val title: String,

    )