package com.example.premiummovies.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovies.data.remotedatasource.utils.Resource
import com.example.premiummovies.domain.repository.MovieRepository
import com.example.premiummovies.presentation.movielist.MovieListingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    var state by mutableStateOf(MovieListingsState())

    /*private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()*/

    init {
        getMovieList()
    }

    private fun getMovieList() {
        viewModelScope.launch {
            movieRepository.getTrendingMovies(state.pageNo).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { listings ->
                            state = state.copy(
                                movies = listings.results,
                                pageNo = listings.page
                            )
                        }
                    }

                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }
}