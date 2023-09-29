package com.example.premiummovies.presentation.moviedetails.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovies.R
import com.example.premiummovies.data.remotedatasource.utils.Resource
import com.example.premiummovies.domain.model.genre.GenreData
import com.example.premiummovies.domain.model.moviedetails.SpokenLanguage
import com.example.premiummovies.domain.repository.MovieRepository
import com.example.premiummovies.presentation.moviedetails.MovieDetailState
import com.example.premiummovies.presentation.utils.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val resourcesProvider: ResourcesProvider
) :
    ViewModel() {

    var state by mutableStateOf(MovieDetailState())

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { movie ->
                            state = state.copy(
                                movie = movie,
                                isLoading = false,
                                error = ""
                            )
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            error = result.message ?: genericErrorMessage(),
                            isLoading = false
                        )
                    }

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

    private fun genericErrorMessage() = resourcesProvider.getString(R.string.generic_error_message)

}