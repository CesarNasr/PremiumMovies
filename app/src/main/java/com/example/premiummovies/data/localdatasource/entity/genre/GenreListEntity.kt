package com.example.premiummovies.data.localdatasource.entity.genre

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class GenreListEntity(
    val genres: List<GenreDataEntity>
)