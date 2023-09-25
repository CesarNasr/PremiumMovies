package com.example.premiummovies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.premiummovies.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(movieRepository: MovieRepository) : ViewModel() {



}