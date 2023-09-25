package com.example.premiummovies.data.remotedatasource.api.dto.moviedetails


import com.google.gson.annotations.SerializedName

data class SpokenLanguageDto(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("name")
    val name: String
)