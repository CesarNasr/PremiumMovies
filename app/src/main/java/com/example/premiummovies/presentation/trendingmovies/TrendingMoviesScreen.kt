@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.premiummovies.presentation.trendingmovies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.premiummovies.R
import com.example.premiummovies.presentation.commonui.ErrorView
import com.example.premiummovies.presentation.navigation.Screen
import com.example.premiummovies.presentation.trendingmovies.viewmodel.MoviesViewModel
import com.example.premiummovies.presentation.utils.theme.Amber
import com.example.premiummovies.presentation.utils.theme.PremiumMoviesTheme
import kotlinx.coroutines.launch

@Composable
fun TrendingMoviesScreen(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel()/* ,state: MovieListingsState, onLoadMore: () -> Unit*/
) {
    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    PremiumMoviesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 15.dp, vertical = 10.dp)

            ) {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    viewModel.state.searchQuery
                ) {
                    viewModel.state.searchQuery = it
                    viewModel.onEvent(TrendingMoviesEvents.FilterMovies)
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                TitleText()

                Spacer(modifier = Modifier.height(7.dp))

                viewModel.state.genres?.genres?.let { genres ->
                    FilterChipGroup(genres, viewModel.state.selectedGenre) {
                        viewModel.state.selectedGenre = it
                        viewModel.onEvent(TrendingMoviesEvents.FilterMovies)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (viewModel.state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (viewModel.state.movies.isEmpty() && viewModel.state.error?.isNotBlank() == true && viewModel.state.error?.isNotEmpty() == true) {
                    ErrorView(error = viewModel.state.error) {
                        viewModel.onEvent(TrendingMoviesEvents.LoadMovies)
                    }
                } else {
                    MoviesGrid(viewModel.state,listState, {
                        if (viewModel.canLoadMore())
                            viewModel.onEvent(TrendingMoviesEvents.LoadMovies)

                    }, { movieId ->
                        navController.navigate(Screen.MovieDetailsScreen.withArgs(movieId))
                    })
                }

            }
        }
    }
}


@Composable
fun TitleText() {
    Text(
        text = stringResource(R.string.movies_title),
        style = TextStyle(
            color = Amber,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    )
}


