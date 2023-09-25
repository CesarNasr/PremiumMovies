package com.example.premiummovies.domain.model.dto


import com.google.gson.annotations.SerializedName

data class ApiEntry<T>(
    val data: T,
    val statusCode: Int,
    val statusMessage: String,
    val success: Boolean
)