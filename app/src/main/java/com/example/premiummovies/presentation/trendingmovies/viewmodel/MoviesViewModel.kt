package com.example.premiummovies.presentation.trendingmovies.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovies.R
import com.example.premiummovies.data.remotedatasource.utils.NetworkHelper
import com.example.premiummovies.data.remotedatasource.utils.Resource
import com.example.premiummovies.domain.repository.MovieRepository
import com.example.premiummovies.presentation.trendingmovies.MoviesState
import com.example.premiummovies.presentation.trendingmovies.TrendingMoviesEvents
import com.example.premiummovies.presentation.utils.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val networkHelper: NetworkHelper,
    private val resourcesProvider: ResourcesProvider
) :
    ViewModel() {
    var state by mutableStateOf(MoviesState())
    var searchDelayMs : Long = 500L

    fun onEvent(event: TrendingMoviesEvents) {
        when (event) {
            is TrendingMoviesEvents.LoadMovies -> {
                getMovieList()
                if (event.loadGenres) {
                    getGenresList()
                }
            }

            is TrendingMoviesEvents.FilterMovies -> {
                filterMovieList()
            }
        }
    }


    fun getGenresList() {
        viewModelScope.launch {
            movieRepository.getMovieGenres().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { genres ->
                            state = state.copy(
                                genres = genres,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            genres = null,
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }


    fun getMovieList() {
        viewModelScope.launch {
            movieRepository.getTrendingMovies(state.pageNo + 1).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { listings ->

                            val newList = state.movies.toMutableList()
                            newList.addAll(listings.results)

                            state = state.copy(
                                movies = newList,
                                pageNo = listings.page,
                                isLoading = false,
                                error = ""
                            )
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = result.message ?: genericErrorMessage()
                        )
                    }

                    is Resource.Loading -> {
                        if (state.movies.isEmpty())
                            state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }


    private var filteringJob: Job? = null
    fun filterMovieList() {
        filteringJob?.cancel()
        filteringJob = viewModelScope.launch {
            delay(searchDelayMs)

            movieRepository.getFilteredTrendingMovies(state.selectedGenre, state.searchQuery)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { movies ->
                                state = state.copy(
                                    movies = movies.results.toMutableList(),
                                    isLoading = false,
                                    error = ""
                                )
                            }
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                error = result.message ?: genericErrorMessage()
                            )

                        }

                        is Resource.Loading -> {
                            if (state.movies.isEmpty())
                                state = state.copy(isLoading = result.isLoading)
                        }

                    }
                }
        }
    }


    fun canLoadMore(): Boolean =
        !state.isLoading && state.searchQuery.isBlank() && state.searchQuery.isEmpty() && state.movies.size > 10 && networkHelper.isNetworkAvailable()

    private fun genericErrorMessage() = resourcesProvider.getString(R.string.generic_error_message)

}