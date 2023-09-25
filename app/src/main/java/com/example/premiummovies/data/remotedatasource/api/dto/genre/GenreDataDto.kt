package com.example.premiummovies.data.remotedatasource.api.dto.genre


import com.google.gson.annotations.SerializedName

data class GenreDataDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)