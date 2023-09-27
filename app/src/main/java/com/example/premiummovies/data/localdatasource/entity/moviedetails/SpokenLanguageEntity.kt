package com.example.premiummovies.data.localdatasource.entity.moviedetails


import com.google.gson.annotations.SerializedName

data class SpokenLanguageEntity(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("name")
    val name: String
)