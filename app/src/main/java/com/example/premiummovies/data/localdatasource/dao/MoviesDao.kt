package com.example.premiummovies.data.localdatasource.dao

import androidx.room.*
import com.example.premiummovies.data.localdatasource.entity.movies.MovieDataEntity
import com.example.premiummovies.data.localdatasource.entity.movies.MovieListEntity
import com.example.premiummovies.domain.model.genre.GenreData


@Dao
interface MoviesDao {

    @Query("SELECT * FROM MovieListEntity")
    fun getAll(): MovieListEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(movies: MovieListEntity): Long


    @Query("DELETE FROM MovieListEntity")
    fun deleteAllItems()


    @Transaction
    fun insertMoviesData(movies: MovieListEntity, movieDataDao: MovieDataDao){
        //deleteAllItems()
        insertItem(movies)
        movieDataDao.insertMoviesData(movies.results)
    }

    @Transaction
    fun getAllMovieData(movieDataDao: MovieDataDao, searchQuery: String = ""): MovieListEntity {
        val moviesEntity = getAll()
       val movieList = movieDataDao.getMoviesByQuery(searchQuery)

        moviesEntity.results = movieList

        return moviesEntity
    }


    @Transaction
    fun getMoviesByGenre(movieDataDao: MovieDataDao, genre: GenreData): MovieListEntity{
        val genredMovies = mutableListOf<MovieDataEntity>()
        val moviesEntity = getAll()
        val movieList = movieDataDao.getAll()

        movieList.forEach {
            if(it.genreIds.contains(genre.id)){
                genredMovies.add(it)
            }
        }

        moviesEntity.results = genredMovies

        return moviesEntity
    }


    /**
     *
     *
     */
}