package com.example.premiummovies.data.remotedatasource.utils

import retrofit2.Response

/**
 * Responsible for converting api response to resource class
 */
class ResponseConverter {

    fun <P,T> responseToResult(response: Response<T>, mapper: (body : T) -> P): Resource<P> {
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val mappedBody = mapper.invoke(body)
                return Resource.Success(mappedBody)
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

/*

 class ResponseConverter{

    // we'll use this function in all
    // repos to handle api errors.
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        // Returning api response
        // wrapped in Resource class
        return withContext(Dispatchers.IO) {
            try {

                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
                    Resource.Success(data = response.body()!!)
                } else {
                    // parsing api's own custom json error
                    // response in ExampleErrorResponse pojo
                    // Simply returning api's own failure message
                    Resource.Error(message =  "Something went wrong")
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                Resource.Error(message = "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                Resource.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                Resource.Error(message = "Something went wrong")
            }
        }
    }

*/
/*    // If you don't wanna handle api's own
    // custom error response then ignore this function
    private fun convertErrorBody(errorBody: ResponseBody?): ExampleErrorResponse? {
        return try {
            errorBody?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ExampleErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }*//*

}
*/
