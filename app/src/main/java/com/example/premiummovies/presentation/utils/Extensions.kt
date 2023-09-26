package com.example.premiummovies.presentation.utils

fun String.getImageUrl(width: Int): String {
    return "https://image.tmdb.org/t/p/w${width}$this"
}


fun String.getYear(): String {
    return this.split("-")[0]
}