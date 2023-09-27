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
    fun insertMoviesData(movies: MovieListEntity, movieDataDao: MovieDataDao) {
        //deleteAllItems()
        insertItem(movies)
        movieDataDao.insertMoviesData(movies.results)
    }

    @Transaction
    fun getAllMovieData(
        movieDataDao: MovieDataDao,
    ): MovieListEntity {
        val moviesEntity = getAll()
        val movieList = movieDataDao.getAll()
        moviesEntity.results = movieList
        return moviesEntity
    }


    @Transaction
    fun getMoviesByGenreAndQuery(
        movieDataDao: MovieDataDao,
        genre: GenreData?,
        searchedQuery: String = ""
    ): MovieListEntity {
        val moviesEntity = getAll()
        val filteredMovieList = movieDataDao.getMoviesByQuery(searchedQuery).toMutableList()
        val genredMovieList = mutableListOf<MovieDataEntity>()

        if (genre != null) {
            filteredMovieList.forEach { movieData ->
                if (movieData.genreIds.contains(genre.id)) {
                    genredMovieList.add(movieData)
                }
            }
            moviesEntity.results = genredMovieList
        } else {
            moviesEntity.results = filteredMovieList
        }

        return moviesEntity
    }


    /**
     *
     *
     */
}