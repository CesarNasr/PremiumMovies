package com.example.premiummovies.data.localdatasource.dao

import androidx.room.*
import com.example.premiummovies.data.localdatasource.entity.genre.GenreDataEntity
import com.example.premiummovies.data.localdatasource.entity.movies.MovieDataEntity
import com.example.premiummovies.data.localdatasource.entity.movies.MovieListEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow

/**
 * Used ROOM database for easily creating SQLITE database tables, insert items, delete items and QUERY many other conditions
 */

@Dao
interface GenresDao {

    @Query("SELECT * FROM GenreDataEntity")
    fun getAll(): List<GenreDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(genres: List<GenreDataEntity>): List<Long>


    @Query("DELETE FROM GenreDataEntity")
    fun deleteAllItems()


    @Transaction
    fun insertGenresData(genres: List<GenreDataEntity>){
        deleteAllItems()
        insertItems(genres)
    }



}