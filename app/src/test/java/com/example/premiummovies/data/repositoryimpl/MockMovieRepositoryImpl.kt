package com.example.premiummovies.data.repositoryimpl

import com.example.premiummovies.data.remotedatasource.utils.Resource
import com.example.premiummovies.domain.model.genre.GenreData
import com.example.premiummovies.domain.model.genre.GenreList
import com.example.premiummovies.domain.model.moviedetails.MovieDetails
import com.example.premiummovies.domain.model.moviedetails.SpokenLanguage
import com.example.premiummovies.domain.model.movies.MovieData
import com.example.premiummovies.domain.model.movies.MovieList
import com.example.premiummovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockMovieRepositoryImpl : MovieRepository {
    private var movieList: MovieList? = null
    private var genreList: GenreList? = null
    private var movieDetails: MovieDetails? = null

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    fun resetRepoData() {
        movieList = null
        genreList = null
        movieDetails = null
    }

    override suspend fun getMovieGenres(): Flow<Resource<GenreList>> {
        return flowOf(
            if (shouldReturnNetworkError) {
                Resource.Error("")
            } else {
                genreList = generateMockGenreList()
                Resource.Success(genreList!!)
            }
        )
    }

    override suspend fun getTrendingMovies(page: Int): Flow<Resource<MovieList>> {
        return flowOf(
            if (shouldReturnNetworkError) {
                Resource.Error("")
            } else {
                movieList = (generateMockMovieList())
                Resource.Success(movieList!!)
            }
        )
    }

    override suspend fun getFilteredTrendingMovies(
        genre: GenreData?,
        searchQuery: String
    ): Flow<Resource<MovieList>> {
        return flowOf(
            if (shouldReturnNetworkError) {
                Resource.Error("")
            } else {
                movieList = (generateMockMovieList())
                Resource.Success(movieList!!)
            }
        )
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> {
        return flowOf(Resource.Success(mockMovieDetails()))
    }


    private fun generateMockMovieList(): MovieList {
        val movies = mutableListOf<MovieData>()
        for (i in 0 until 10) {
            movies.add(
                MovieData(
                    "www.backdroppath.com",
                    listOf(1, 2, 3),
                    i,
                    "Original Title $i",
                    "www.posterPath.com",
                    "2023-1-1",
                    "Title $i"
                )
            )
        }
        return MovieList(0, movies, 1, 10)
    }


    private fun generateMockGenreList(): GenreList {
        val genres = mutableListOf<GenreData>()
        for (i in 0 until 10) {
            genres.add(GenreData(i, "genre no. $i"))
        }
        return GenreList(genres)
    }

    private fun mockMovieDetails(): MovieDetails {
        val genres = mutableListOf<GenreData>()
        val spokenLanguage = mutableListOf<SpokenLanguage>()

        for (i in 0 until 10) {
            genres.add(GenreData(i, "genre no. $i"))
        }
        for (i in 0 until 10) {
            spokenLanguage.add(SpokenLanguage("Language $i", "Language $i"))
        }
        return MovieDetails(
            "www.backdroppath.com",
            1000,
            genres,
            1,
            "Original title",
            "Overview",
            "www.posterPath.com",
            "2023-1-1",
            1000,
            1000,
            spokenLanguage,
            "released",
            "tagline",
            "title 1",
            "homepage"
        )
    }
}