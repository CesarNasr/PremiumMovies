package com.example.premiummovies.data.localdatasource.dao

import androidx.room.*
import com.example.premiummovies.data.localdatasource.entity.movies.MovieListEntity


@Dao
interface MoviesDao {

    @Query("SELECT * FROM MovieListEntity")
    fun getAll(): MovieListEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(movies: MovieListEntity): Long


    @Query("DELETE FROM MovieListEntity")
    fun deleteAllItems()


    @Transaction
    fun insertMoviesData(movies: MovieListEntity){
        //deleteAllItems()
        insertItem(movies)
    }

    /**
     *
     *
     */
}
