package com.example.premiummovies.data.repositoryimpl

import com.example.premiummovies.BuildConfig
import com.example.premiummovies.data.localdatasource.database.AppDatabase
import com.example.premiummovies.data.localdatasource.entity.genre.GenreListEntity
import com.example.premiummovies.data.mapper.GenresMapper
import com.example.premiummovies.data.mapper.MovieDetailsMapper
import com.example.premiummovies.data.mapper.MovieMapper
import com.example.premiummovies.data.remotedatasource.api.remote.MovieApiService
import com.example.premiummovies.data.remotedatasource.utils.Resource
import com.example.premiummovies.data.remotedatasource.utils.ResponseConverter
import com.example.premiummovies.domain.model.genre.GenreData
import com.example.premiummovies.domain.model.genre.GenreList
import com.example.premiummovies.domain.model.moviedetails.MovieDetails
import com.example.premiummovies.domain.model.movies.MovieList
import com.example.premiummovies.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val responseConverter: ResponseConverter,
    private val genresMapper: GenresMapper,
    private val movieMapper: MovieMapper,
    private val db: AppDatabase,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val ioDispatcher: CoroutineDispatcher,
    ) : MovieRepository {

    private val apiKey = BuildConfig.API_KEY
    private val includeAdultMovies = false
    private val sortBy = "popularity.desc"

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

                    is Resource.Loading -> {

                    }
                }
            } catch (e: IOException) {
                emit(fetchLocalGenres())
            } catch (e : Exception){
                emit(Resource.Error("Please check your internet connection"))
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

                    is Resource.Loading -> {

                    }
                }
            } catch (e: IOException) {
                emit(fetchLocalTrendingMovies())
            } catch (e : Exception){
                emit(Resource.Error("Please check your internet connection"))
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
                emit(Resource.Error("Please check your internet connection"))
            }

        }.flowOn(ioDispatcher)
    }

    private fun fetchLocalTrendingMovies(): Resource<MovieList> {
        val localMovies = db.MoviesDao().getAllMovieData(db.MovieDataDao())
        return if (localMovies != null)
            Resource.Success(movieMapper.mapFromMovieListEntity(localMovies))
        else Resource.Error("Please check your internet connection")

    }

    private fun fetchFilteredLocalTrendingMovies(
        genre: GenreData?,
        searchQuery: String = ""
    ): Resource<MovieList> {
        val localMovies = db.MoviesDao().getMoviesByGenreAndQuery(db.MovieDataDao(), genre, searchQuery)
        return if (localMovies != null)
            Resource.Success(movieMapper.mapFromMovieListEntity(localMovies))
        else Resource.Error("Please check your internet connection")
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
                emit(Resource.Error("Please check your internet connection"))

            }
        }
    }
}