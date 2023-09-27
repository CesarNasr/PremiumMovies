package com.example.premiummovies.domain.repository

import com.example.premiummovies.data.remotedatasource.utils.Resource
import com.example.premiummovies.domain.model.genre.GenreData
import com.example.premiummovies.domain.model.genre.GenreList
import com.example.premiummovies.domain.model.moviedetails.MovieDetails
import com.example.premiummovies.domain.model.movies.MovieList
import kotlinx.coroutines.flow.Flow


interface MovieRepository {

    suspend fun getMovieGenres(): Flow<Resource<GenreList>>

    suspend fun getTrendingMovies(page: Int = 0): Flow<Resource<MovieList>>

    suspend fun getFilteredTrendingMovies(genre : GenreData?, searchQuery : String = "") : Flow<Resource<MovieList>>

    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>

}
