package com.example.premiummovies.presentation.utils

import com.example.premiummovies.data.remotedatasource.utils.IMAGE_BASE_URL

fun String.getImageUrl(width: Int): String {
    return "${IMAGE_BASE_URL + width}$this"
}

fun String.getYear(): String {
    return this.split("-")[0]
}