package com.example.premiummovies.data.localdatasource.entity.movies


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo("page")
    val page: Int,
    @ColumnInfo("results")
    val results: List<MovieDataEntity>,
    @ColumnInfo("total_pages")
    val totalPages: Int,
    @ColumnInfo("total_results")
    val totalResults: Int
)