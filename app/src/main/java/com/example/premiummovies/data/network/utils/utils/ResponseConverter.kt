package com.example.premiummovies.data.network.utils.utils

import retrofit2.Response

/**
 * Responsible for converting api response to resource class
 */
class ResponseConverter {

    fun responseToResult(response: Response<ApiEntry>): Resource<ApiEntry> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        response.errorBody()?.let {
            return Resource.Error(
                it.string(),
            )
        }
        return Resource.Error(response.message())
    }

}