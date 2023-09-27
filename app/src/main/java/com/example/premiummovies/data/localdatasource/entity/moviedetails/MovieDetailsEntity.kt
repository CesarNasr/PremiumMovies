package com.example.premiummovies.data.localdatasource.entity.moviedetails


import com.example.premiummovies.data.localdatasource.entity.genre.GenreDataEntity
import com.google.gson.annotations.SerializedName

data class MovieDetailsEntity(

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("budget")
    val budget: Int,

    @SerializedName("genres")
    val genres: List<GenreDataEntity>,

    @SerializedName("id")
    val id: Int,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("revenue")
    val revenue: Long,

    @SerializedName("runtime")
    val runtime: Int,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageEntity>,

    @SerializedName("status")
    val status: String,

    @SerializedName("tagline")
    val tagline: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("homepage")
    val homePage: String,
    )