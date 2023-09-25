package com.example.premiummovies.data.remotedatasource.api.dto.movies


import com.google.gson.annotations.SerializedName

data class MovieListDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDataDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)