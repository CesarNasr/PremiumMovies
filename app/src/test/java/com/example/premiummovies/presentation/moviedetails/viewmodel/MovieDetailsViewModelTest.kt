package com.example.premiummovies.presentation.moviedetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.premiummovies.MainCoroutineRule
import com.example.premiummovies.data.repositoryimpl.MockMovieRepositoryImpl
import com.example.premiummovies.domain.model.genre.GenreData
import com.example.premiummovies.domain.model.moviedetails.SpokenLanguage
import com.example.premiummovies.presentation.utils.ResourcesProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MovieDetailsViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: MovieDetailsViewModel


    private lateinit var mockRepositoryImpl: MockMovieRepositoryImpl
    private lateinit var resourcesProvider: ResourcesProvider

    @Before
    fun setup() {
        mockRepositoryImpl = MockMovieRepositoryImpl()
        resourcesProvider = ResourcesProvider(ApplicationProvider.getApplicationContext())
        viewModel = MovieDetailsViewModel(mockRepositoryImpl, resourcesProvider)
    }


    @After
    fun tearDown() {
    }


    @Test
    fun `fetch movie return success`() = runTest {
        mockRepositoryImpl.resetRepoData()
        viewModel.getMovieDetails(1)
        val response = viewModel.state.movie

        assertThat(response).isNotNull()
    }

    @Test
    fun `fetch movie return error`() = runTest {

        mockRepositoryImpl.setShouldReturnNetworkError(true)

        mockRepositoryImpl.resetRepoData()

        viewModel.getMovieDetails(1)
        val response = viewModel.state.movie

        assertThat(response).isNull()
    }


    @Test
    fun `get genre string from list success`() = runTest {
        val result = viewModel.getGenresStringList(
            listOf(
                GenreData(1, "1"),
                GenreData(2, "2"),
                GenreData(3, "3")
            )
        )
        assertThat(result).isEqualTo("1,2,3")
    }


    @Test
    fun `get language string from list success`() = runTest {
        val result = viewModel.getLanguagesStringList(
            listOf(
                SpokenLanguage("English", "English"),
                SpokenLanguage("French", "French"),
                SpokenLanguage("Arabic", "Arabic")
            )
        )
        assertThat(result).isEqualTo("English,French,Arabic")
    }


}