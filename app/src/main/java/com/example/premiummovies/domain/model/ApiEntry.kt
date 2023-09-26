package com.example.premiummovies.domain.model


data class ApiEntry<T>(
    val data: T,
    val statusCode: Int,
    val statusMessage: String,
    val success: Boolean
)