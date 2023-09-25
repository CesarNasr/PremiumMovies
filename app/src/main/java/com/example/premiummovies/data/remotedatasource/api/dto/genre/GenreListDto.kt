package com.example.premiummovies.data.remotedatasource.api.dto.genre

import com.google.gson.annotations.SerializedName

data class GenreListDto(
    @SerializedName("genres")
    val genres: List<GenreDataDto>
)

