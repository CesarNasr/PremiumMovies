package com.example.premiummovies.data.localdatasource.entity.movies


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class MovieListEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo("page")
    var page: Int,
    @ColumnInfo("total_pages")
    var totalPages: Int,
    @ColumnInfo("total_results")
    var totalResults: Int,

    @Ignore
    var results: List<MovieDataEntity>,
) {
    constructor() : this(-1, -1, -1, -1, listOf())
}