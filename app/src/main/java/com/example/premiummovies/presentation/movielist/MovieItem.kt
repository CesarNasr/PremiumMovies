package com.example.premiummovies.presentation.movielist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.premiummovies.domain.model.dto.movies.MovieData

@Composable
fun MovieItem(
    movie: MovieData,
    modifier: Modifier = Modifier,
    onClick: (MovieData) -> Unit
) {
    Column(
        modifier = modifier.padding(3.dp).clickable {
            onClick(movie)
        }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            model = "https://image.tmdb.org/t/p/w500/${movie.backdropPath}",
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = movie.title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = movie.releaseDate,
            fontWeight = FontWeight.Light,
        )
    }
}