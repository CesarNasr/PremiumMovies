package com.example.premiummovies.presentation.moviedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.premiummovies.R
import com.example.premiummovies.presentation.moviedetails.viewmodel.MovieDetailsViewModel
import com.example.premiummovies.presentation.utils.getImageUrl
import com.example.premiummovies.presentation.utils.getYear
import com.example.premiummovies.presentation.commonui.ErrorView

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getMovieDetails(movieId)
    }

    val state = viewModel.state

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (viewModel.state.movie == null && viewModel.state.error?.isNotBlank() == true && viewModel.state.error?.isNotEmpty() == true) {
        ErrorView(error = viewModel.state.error) {
            viewModel.getMovieDetails(movieId)
        }
    } else {
        MovieDetailsBody(state, viewModel)
    }
}


@Composable
fun MovieDetailsBody(state: MovieDetailState, viewModel: MovieDetailsViewModel) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)),

            model = "${state.movie?.backdropPath}".getImageUrl(500),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_image_placeholder),
            error = painterResource(R.drawable.ic_brokenimage_placeholder)
        )

        Row(modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomEnd = 8.dp,
                        bottomStart = 8.dp
                    )
                ),

                model = state.movie?.posterPath?.getImageUrl(200),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_image_placeholder),
                error = painterResource(R.drawable.ic_brokenimage_placeholder)
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = state.movie?.title + " (${state.movie?.releaseDate?.getYear()})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = viewModel.getGenresStringList(state.movie?.genres),
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.padding(start = 10.dp),
            fontWeight = FontWeight.SemiBold,
            text = state.movie?.overview ?: "",
        )

        Spacer(modifier = Modifier.height(70.dp))

        Column(modifier = Modifier.padding(10.dp)) {
            BuildAnnotatedString(state.movie?.homePage)
            BuildWeighedString(
                "Languages",
                viewModel.getLanguagesStringList(state.movie?.spokenLanguages)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BuildWeighedString(stringResource(R.string.status), state.movie?.status ?: "")
                BuildWeighedString(
                    stringResource(R.string.runtime),
                    state.movie?.runtime.toString()
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BuildWeighedString(
                    stringResource(R.string.budget),
                    state.movie?.budget.toString() + stringResource(R.string.minutes)
                )
                BuildWeighedString(
                    stringResource(R.string.revenue),
                    state.movie?.revenue.toString() + stringResource(R.string.usd)
                )
            }
        }
    }

}

@Composable
fun BuildAnnotatedString(url: String?) {
    url?.let {

        val uriHandler = LocalUriHandler.current

        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(R.string.homepage))
            }
            pushStringAnnotation(tag = "url", annotation = url)
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append(url)
            }
        }

        ClickableText(
            text = annotatedString,
            style = MaterialTheme.typography.bodyMedium,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "url", start = offset, end = offset)
                    .firstOrNull()?.let {
                        uriHandler.openUri(url)
                    }
            })
    }

}


@Composable
fun BuildWeighedString(title: String, value: String) {
    val boldString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$title: ")
        }
        withStyle(style = SpanStyle()) {
            append(value)
        }
    }

    Text(
        text = boldString,
        style = MaterialTheme.typography.bodyMedium,
    )
}