package com.example.premiummovies.data.remotedatasource.api.dto


import com.google.gson.annotations.SerializedName

data class ApiEntry<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("success")
    val success: Boolean
)