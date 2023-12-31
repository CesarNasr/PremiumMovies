package com.example.premiummovies.data.remotedatasource.utils
/**
 * Sealed Class to monitor the API calls state and update the UI, can hold dynamic data and messages
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>(null)
    class Error<T>(message: String? = null, data: T? = null) : Resource<T>(data, message)
}
