package com.example.premiummovies.data.localdatasource.entity.movies


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDataEntity(
    @PrimaryKey(autoGenerate = true )
    val dbId: Int? = null,

    @ColumnInfo("id")
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