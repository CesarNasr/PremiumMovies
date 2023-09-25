package com.example.premiummovies.domain.repository

import com.example.premiummovies.data.remotedatasource.utils.Resource
import com.example.premiummovies.domain.model.dto.genre.GenreList
import com.example.premiummovies.domain.model.dto.moviedetails.MovieDetails
import com.example.premiummovies.domain.model.dto.movies.MovieList
import kotlinx.coroutines.flow.Flow


interface MovieRepository {

    suspend fun getMovieGenres(): Flow<Resource<GenreList>>

    suspend fun getTrendingMovies(page: Int): Flow<Resource<MovieList>>

    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>
}
