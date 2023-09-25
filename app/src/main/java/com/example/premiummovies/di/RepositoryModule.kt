package com.example.premiummovies.di


import com.example.premiummovies.data.mapper.GenresMapper
import com.example.premiummovies.data.mapper.MovieDetailsMapper
import com.example.premiummovies.data.mapper.MovieMapper
import com.example.premiummovies.data.remotedatasource.utils.ResponseConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency injection class that provides instances related to repositories
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideResponseConverter(): ResponseConverter {
        return ResponseConverter()
    }

    @Provides
    @Singleton
    fun provideGenresMapper() = GenresMapper()

    @Provides
    @Singleton
    fun provideMovieMapper() = MovieMapper()

    @Provides
    @Singleton
    fun provideMovieDetailsMapper() = MovieDetailsMapper()
}