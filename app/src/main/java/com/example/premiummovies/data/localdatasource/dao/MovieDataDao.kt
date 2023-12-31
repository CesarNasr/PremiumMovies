package com.example.premiummovies.data.localdatasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.premiummovies.data.localdatasource.entity.movies.MovieDataEntity

/**
 * Used ROOM database for easily creating SQLITE database tables, insert items, delete items and QUERY many other conditions
 */

@Dao
interface MovieDataDao {

    @Query("SELECT * FROM MovieDataEntity ORDER BY dbId ASC")
    fun getAll(): List<MovieDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(movies: List<MovieDataEntity>): List<Long>


    @Query("DELETE FROM MovieDataEntity")
    fun deleteAllItems()


    @Transaction
    fun insertMoviesData(movies: List<MovieDataEntity>){
        //deleteAllItems()
        insertItem(movies)
    }

    @Query("SELECT * FROM MovieDataEntity WHERE title LIKE '%' || :searchQuery || '%'")
    fun getMoviesByQuery(searchQuery : String): List<MovieDataEntity>

    /**
     *
     *
     */
}
