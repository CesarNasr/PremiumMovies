@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.premiummovies.presentation.trendingmovies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.premiummovies.R
import com.example.premiummovies.presentation.navigation.Screen
import com.example.premiummovies.presentation.trendingmovies.viewmodel.MoviesViewModel
import com.example.premiummovies.presentation.utils.theme.Amber
import com.example.premiummovies.presentation.utils.theme.PremiumMoviesTheme

@Composable
fun TrendingMoviesScreen(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel()/* ,state: MovieListingsState, onLoadMore: () -> Unit*/
) {

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
                    viewModel.searchMovieList(it)
                }

                Spacer(modifier = Modifier.height(10.dp))

                TitleText()

                Spacer(modifier = Modifier.height(7.dp))

                viewModel.state.genres?.genres?.let { genres ->
                    FilterChipGroup(genres, viewModel.state.selectedGenre) {
                        viewModel.state.selectedGenre = it
                      viewModel.filterByGenre(it)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                MoviesGrid(viewModel.state, {
                    viewModel.getMovieList()
                }, { movieId ->
                    navController.navigate(Screen.MovieDetailsScreen.withArgs(movieId))
                })
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


