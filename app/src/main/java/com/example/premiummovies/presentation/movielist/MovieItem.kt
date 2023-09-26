package com.example.premiummovies.presentation.movielist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.premiummovies.domain.model.movies.MovieData
import com.example.premiummovies.R
import com.example.premiummovies.presentation.utils.getImageUrl
import com.example.premiummovies.presentation.utils.getYear

@Composable
fun MovieItem(
    movie: MovieData,
    modifier: Modifier = Modifier,
    onClick: (MovieData) -> Unit
) {
    Card(
        modifier = modifier
            .padding(3.dp)
            .background(Color.Transparent)
    ) {

        Column(
            modifier = modifier
                .padding(3.dp)
                .clickable {
                    onClick(movie)
                }
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth().clip(RoundedCornerShape(8.dp)),
                model = movie.posterPath.getImageUrl(200),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_image_placeholder),
                error = painterResource(R.drawable.ic_brokenimage_placeholder)
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
                text = movie.releaseDate.getYear(),
                fontWeight = FontWeight.Light,
            )
        }
    }
}