package com.example.premiummovies.presentation.commonui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.premiummovies.R

/**
 * Reusable Error component
 */
@Composable
fun ErrorView(modifier: Modifier = Modifier, error: String?, onRetryClicked: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                onRetryClicked()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(painter = painterResource(R.drawable.ic_error), contentDescription = null)
        Text(text = error ?: "")
    }
}
