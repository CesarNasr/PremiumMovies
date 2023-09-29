package com.example.premiummovies.data.repositoryimpl

import com.example.premiummovies.BuildConfig
import com.example.premiummovies.R
import com.example.premiummovies.data.localdatasource.database.AppDatabase
import com.example.premiummovies.data.localdatasource.entity.genre.GenreListEntity
import com.example.premiummovies.data.mapper.GenresMapper
import com.example.premiummovies.data.mapper.MovieDetailsMapper
import com.example.premiummovies.data.mapper.MovieMapper
import com.example.premiummovies.data.remotedatasource.api.remote.MovieApiService
import com.example.premiummovies.data.remotedatasource.utils.Resource
import com.example.premiummovies.data.remotedatasource.utils.ResponseConverter
import com.example.premiummovies.data.remotedatasource.utils.SORTING_CRITERIA
import com.example.premiummovies.domain.model.genre.GenreData
import com.example.premiummovies.domain.model.genre.GenreList
import com.example.premiummovies.domain.model.moviedetails.MovieDetails
import com.example.premiummovies.domain.model.movies.MovieList
import com.example.premiummovies.domain.repository.MovieRepository
import com.example.premiummovies.presentation.utils.ResourcesProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

import javax.inject.Inject
/**
 * This repository implementation acts as the access point to our data layer, making data decisions to fetch data from different sources
 * in our case, either from remote datasource or from our local db, depending on the network connectivity
 * */

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val responseConverter: ResponseConverter,
    private val genresMapper: GenresMapper,
    private val movieMapper: MovieMapper,
    private val db: AppDatabase,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val ioDispatcher: CoroutineDispatcher) : MovieRepository {

    private val apiKey = BuildConfig.API_KEY
    private val includeAdultMovies = false
    private val sortBy = SORTING_CRITERIA

    override suspend fun getMovieGenres(): Flow<Resource<GenreList>> {

        return flow {
            try {
                emit(Resource.Loading())
                val response = movieApiService.getMovieGenres(apiKey = apiKey)

                val result = responseConverter.responseToResult(response = response) {
                    genresMapper.mapFromDto(it)
                }

                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            db.GenresDao()
                                .insertGenresData(genresMapper.mapToEntity(result.data).genres)
                            emit(result)
                        }
                    }

                    is Resource.Error -> {
                        emit(fetchLocalGenres())
                    }

                    is Resource.Loading -> {}
                }
            } catch (e: IOException) {
                emit(fetchLocalGenres())
            } catch (e : Exception){
                emit(Resource.Error())
            } as Unit
        }.flowOn(ioDispatcher)
    }

    private fun fetchLocalGenres(): Resource.Success<GenreList> {
        val localGenres = db.GenresDao().getAll()
        val result = (genresMapper.mapFromLocalEntity(GenreListEntity(localGenres)))
        return Resource.Success(result)
    }

    override suspend fun getTrendingMovies(
        page: Int
    ): Flow<Resource<MovieList>> {
        return flow {
            try {
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

                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            db.MoviesDao()
                                .insertMoviesData(
                                    movieMapper.mapToMovieListEntity(result.data),
                                    db.MovieDataDao()
                                )
                            emit(result)
                        }
                    }

                    is Resource.Error -> {
                        emit(fetchLocalTrendingMovies())
                    }

                    is Resource.Loading -> {}
                }
            } catch (e: IOException) {
                emit(fetchLocalTrendingMovies())
            } catch (e : Exception){
                emit(Resource.Error())
            } as Unit
        }.flowOn(ioDispatcher)
    }

    override suspend fun getFilteredTrendingMovies(
        genre: GenreData?,
        searchQuery: String
    ): Flow<Resource<MovieList>> {
        return flow {
            try {
                emit(Resource.Loading())

                emit(fetchFilteredLocalTrendingMovies(genre, searchQuery))
            } catch (e: IOException) {
                emit(Resource.Error())
            }

        }.flowOn(ioDispatcher)
    }

    private fun fetchLocalTrendingMovies(): Resource<MovieList> {
        val localMovies = db.MoviesDao().getAllMovieData(db.MovieDataDao())
        return if (localMovies != null)
            Resource.Success(movieMapper.mapFromMovieListEntity(localMovies))
        else Resource.Error()

    }

    private fun fetchFilteredLocalTrendingMovies(
        genre: GenreData?,
        searchQuery: String = ""
    ): Resource<MovieList> {
        val localMovies = db.MoviesDao().getMoviesByGenreAndQuery(db.MovieDataDao(), genre, searchQuery)
        return if (localMovies != null)
            Resource.Success(movieMapper.mapFromMovieListEntity(localMovies))
        else Resource.Error()
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> {
        return flow {
            try {

                emit(Resource.Loading())

                val response = movieApiService.getMovieDetails(movieId = movieId, apiKey = apiKey)

                val result = responseConverter.responseToResult(response = response) {
                    movieDetailsMapper.mapFromDto(it)
                }
                emit(result)
            } catch (e: IOException) {
                emit(Resource.Error())

            }
        }
    }


    //private val errorString = resourcesProvider.getString(R.string.generic_error_message)
}