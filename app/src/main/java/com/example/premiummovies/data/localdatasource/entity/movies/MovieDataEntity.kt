package com.example.premiummovies.data.localdatasource.entity.movies


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import androidx.room.TypeConverter

@Entity
data class MovieDataEntity(
    @ColumnInfo("id" )
    val id: Int,

    @ColumnInfo("backdrop_path")
    val backdropPath: String?,

    @ColumnInfo("genre_ids")
    val genreIds: List<Int>,

    @ColumnInfo("original_title")
    val originalTitle: String,

    @ColumnInfo("poster_path")
    val posterPath: String,

    @ColumnInfo("release_date")
    val releaseDate: String,

    @ColumnInfo("title")
    val title: String,
)