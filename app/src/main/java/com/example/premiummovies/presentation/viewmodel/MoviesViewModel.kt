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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    var state by mutableStateOf(MovieListingsState())
    var movieDetailsState by mutableStateOf(MovieListingsState())

    /*private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()*/

    init {
        getMoviesData()
    }

    fun getMovieList() {
        viewModelScope.launch {
            movieRepository.getTrendingMovies(state.pageNo + 1).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { listings ->

                            val newList = state.movies
                            newList.addAll(listings.results)

                            state = state.copy(
                                movies = newList,
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


    private fun getGenresList() {
        viewModelScope.launch {
            movieRepository.getMovieGenres().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { genres ->
                            state = state.copy(
                                genres = genres
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



    fun getMoviesData(){
        getMovieList()
        getGenresList()
    }
}