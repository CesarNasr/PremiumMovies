package com.example.premiummovies.data.repositoryimpl

import com.example.premiummovies.BuildConfig
import com.example.premiummovies.data.mapper.GenresMapper
import com.example.premiummovies.data.mapper.MovieDetailsMapper
import com.example.premiummovies.data.mapper.MovieMapper
import com.example.premiummovies.data.remotedatasource.api.remote.MovieApiService
import com.example.premiummovies.data.remotedatasource.utils.Resource
import com.example.premiummovies.data.remotedatasource.utils.ResponseConverter
import com.example.premiummovies.domain.model.dto.genre.GenreList
import com.example.premiummovies.domain.model.dto.moviedetails.MovieDetails
import com.example.premiummovies.domain.model.dto.movies.MovieList
import com.example.premiummovies.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val responseConverter: ResponseConverter,
    private val genresMapper: GenresMapper,
    private val movieMapper: MovieMapper,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val ioDispatcher: CoroutineDispatcher

) : MovieRepository {

    private val apiKey = BuildConfig.API_KEY
    private val includeAdultMovies = false
    private val sortBy = "popularity.desc"

    override suspend fun getMovieGenres(): Flow<Resource<GenreList>> {

        return flow {
            emit(Resource.Loading())
            val response = movieApiService.getMovieGenres(apiKey = apiKey)

            val result = responseConverter.responseToResult(response = response) {
                genresMapper.mapFromDto(it)
            }
            emit(result)
        }.flowOn(ioDispatcher)

    }

    override suspend fun getTrendingMovies(page: Int): Flow<Resource<MovieList>> {
        return flow {
            emit(Resource.Loading())
            val response = movieApiService.getTrendingMovies(
                includeAdult = includeAdultMovies,
                sortBy = sortBy,
                page = page,
                apiKey = apiKey
            )

            val result = responseConverter.responseToResult(response = response) {
                movieMapper.mapToMovieListDomain(it)
            }
            emit(result)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> {
        return flow {
            emit(Resource.Loading())

            val response = movieApiService.getMovieDetails(movieId = movieId, apiKey = apiKey)

            val result = responseConverter.responseToResult(response = response) {
                movieDetailsMapper.mapFromDto(it)
            }
            emit(result)
        }
    }


}