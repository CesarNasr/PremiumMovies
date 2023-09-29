package com.example.premiummovies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.premiummovies.presentation.moviedetails.MovieDetailsScreen
import com.example.premiummovies.presentation.trendingmovies.TrendingMoviesScreen
/**
 * Navigation Controller file
 */

private const val ARG_MOVIE_ID = "movieId"

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MovieListScreen.route) {
        composable(route = Screen.MovieListScreen.route) {
            TrendingMoviesScreen(navController = navController)
        }
        composable(
            route = Screen.MovieDetailsScreen.route + "/{$ARG_MOVIE_ID}", // optional : "?movieId={movieId}"
            arguments = listOf(
                navArgument(ARG_MOVIE_ID) {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                }
            ),
            content = { entry ->
                MovieDetailsScreen(movieId = entry.arguments?.getInt(ARG_MOVIE_ID) ?: -1)
            }
        )
    }
}


