package com.example.premiummovies.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovies.data.remotedatasource.utils.Resource
import com.example.premiummovies.domain.model.genre.GenreData
import com.example.premiummovies.domain.model.moviedetails.SpokenLanguage
import com.example.premiummovies.domain.repository.MovieRepository
import com.example.premiummovies.presentation.movielist.MovieDetailState
import com.example.premiummovies.presentation.movielist.MovieListingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    var state by mutableStateOf(MovieDetailState())

    /*private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()*/

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { movie ->
                            state = state.copy(
                                movie = movie,
                                isLoading = false
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



    fun getGenresStringList(genres: List<GenreData>?): String {
        val result = StringBuilder()
        genres?.forEachIndexed { index, genre ->
            result.append(genre.name)
            if (index != genres.size - 1) {
                result.append(",")
            }
        }

        return result.toString()
    }

    fun getLanguagesStringList(languages: List<SpokenLanguage>?): String {
        val result = StringBuilder()
        languages?.forEachIndexed { index, language ->
            result.append(language.name)
            if (index != languages.size - 1) {
                result.append(",")
            }
        }

        return result.toString()
    }
}