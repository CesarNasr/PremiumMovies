package com.example.premiummovies.presentation.trendingmovies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.premiummovies.MainCoroutineRule
import com.example.premiummovies.data.remotedatasource.utils.NetworkHelper
import com.example.premiummovies.data.repositoryimpl.MockMovieRepositoryImpl
import com.example.premiummovies.presentation.utils.ResourcesProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MoviesViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: MoviesViewModel
    private lateinit var mockRepositoryImpl: MockMovieRepositoryImpl
    private lateinit var resourcesProvider: ResourcesProvider
    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setup() {
        networkHelper = NetworkHelper(ApplicationProvider.getApplicationContext())
        mockRepositoryImpl = MockMovieRepositoryImpl()
        resourcesProvider = ResourcesProvider(ApplicationProvider.getApplicationContext())
        viewModel = MoviesViewModel(mockRepositoryImpl, networkHelper, resourcesProvider)
    }


    @After
    fun tearDown() {
        //mockRepositoryImpl.resetRepoData()
    }

    @Test
    fun `fetch genres data return success`() = runTest {
            mockRepositoryImpl.resetRepoData()
            viewModel.getGenresList()
            val response = viewModel.state.genres

            assertThat(response?.genres).isNotEmpty()
    }

    @Test
    fun `fetch genres data return error`() = runTest{

            mockRepositoryImpl.setShouldReturnNetworkError(true)

            mockRepositoryImpl.resetRepoData()

            viewModel.getGenresList()
            val response = viewModel.state.genres

            assertThat(response?.genres).isNull()
    }


    @Test
    fun `fetch movie data return success`() = runTest {

            mockRepositoryImpl.resetRepoData()
            viewModel.getMovieList()
            val response = viewModel.state.movies

            assertThat(response).isNotEmpty()
    }

    @Test
    fun `fetch movies data return error`() =  runTest {

            mockRepositoryImpl.resetRepoData()
            mockRepositoryImpl.setShouldReturnNetworkError(true)
            runBlocking {
                viewModel.getMovieList()
                val response = viewModel.state.movies

                assertThat(response.size).isEqualTo(0)
            }
    }


    @Test
    fun `fetch filtered movie data return success`() = runTest {
        mockRepositoryImpl.resetRepoData()
        viewModel.searchDelayMs = 0L
        viewModel.filterMovieList()
        val response = viewModel.state.movies

        assertThat(response).isNotEmpty()
    }

    @Test
    fun `fetch filtered movies data return error`() = runTest {
        mockRepositoryImpl.resetRepoData()
        mockRepositoryImpl.setShouldReturnNetworkError(true)
        viewModel.filterMovieList()
        val response = viewModel.state.movies

        assertThat(response.size).isEqualTo(0)
    }


}