package com.example.premiummovies.presentation.trendingmovies.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovies.data.remotedatasource.utils.Resource
import com.example.premiummovies.domain.repository.MovieRepository
import com.example.premiummovies.presentation.trendingmovies.MoviesState
import com.example.premiummovies.presentation.trendingmovies.TrendingMoviesEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    var state by mutableStateOf(MoviesState())

    init {
        getMoviesData()
    }

    fun onEvent(event: TrendingMoviesEvents) {
        when (event) {
            is TrendingMoviesEvents.LoadMovies -> {
                getMoviesData()
            }

            is TrendingMoviesEvents.FilterMovies -> {
                filterMovieList()
            }
        }
    }


private fun getGenresList() {
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
                    state = state.copy(isLoading = false, error = result.message)
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

                        /*   val newList = state.movies
                           newList.addAll(listings.results)*/

                        state = state.copy(
                            movies = listings.results,
                            pageNo = listings.page,
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> {
                    state = state.copy(isLoading = false, error = result.message)
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = result.isLoading)
                }
            }
        }
    }
}


private var filteringJob: Job? = null
private fun filterMovieList() {
    filteringJob?.cancel()
    filteringJob = viewModelScope.launch {
        delay(500L)
        movieRepository.getFilteredTrendingMovies(state.selectedGenre, state.searchQuery)
            .collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { movies ->
                            state = state.copy(
                                movies = movies.results.toMutableList(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(isLoading = false, error = result.message)

                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                }
            }
    }
}

private fun getMoviesData() {
    getMovieList()
    getGenresList()
}

fun canLoadMore(): Boolean =
    !state.isLoading && state.searchQuery.isBlank() && state.searchQuery.isEmpty()

}