package com.example.premiummovies.data.remotedatasource.api.remote

import com.example.premiummovies.data.remotedatasource.api.dto.genre.GenreListDto
import com.example.premiummovies.data.remotedatasource.api.dto.moviedetails.MovieDetailsDto
import com.example.premiummovies.data.remotedatasource.api.dto.movies.MovieListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("3/genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String
    ): Response<GenreListDto>

    @GET("3/discover/movie")
    suspend fun getTrendingMovies(
        @Query("include_adult") includeAdult : Boolean,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieListDto>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDetailsDto>
}