package com.example.premiummovies.di

import android.content.Context
import com.example.premiummovies.BuildConfig
import com.example.premiummovies.data.remotedatasource.api.remote.MovieApiService
import com.example.premiummovies.data.remotedatasource.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Dependency injection class that provides instances related to network utils / api calls
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(networkHelper: NetworkHelper): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        okHttpClientBuilder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            if (networkHelper.isNetworkAvailable()) {
                chain.proceed(chain.request())
            } else {
                throw IOException()
            }
        })

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteAPIService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    fun networkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }
}