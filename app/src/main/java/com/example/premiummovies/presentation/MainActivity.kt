package com.example.premiummovies.presentation

import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.premiummovies.domain.model.dto.movies.MovieList
import com.example.premiummovies.presentation.movielist.MoviesGrid
import com.example.premiummovies.presentation.movielist.SearchBar
import com.example.premiummovies.presentation.ui.theme.Amber
import com.example.premiummovies.presentation.ui.theme.PremiumMoviesTheme
import com.example.premiummovies.presentation.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MoviesViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                            modifier = Modifier.fillMaxWidth()

                        ) {

                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        TitleText()

                        Spacer(modifier = Modifier.height(10.dp))

                        MoviesGrid(viewModel.state)

                    }

                }
            }
        }
    }
}


@Composable
fun TitleText() {
    Text(
        text = "Watch new movies",
        style = TextStyle(
            color = Amber,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PremiumMoviesTheme {
        Greeting("Android")
    }
}