package com.example.premiummovies.di


import com.example.premiummovies.data.localdatasource.database.AppDatabase
import com.example.premiummovies.data.mapper.GenresMapper
import com.example.premiummovies.data.mapper.MovieDetailsMapper
import com.example.premiummovies.data.mapper.MovieMapper
import com.example.premiummovies.data.remotedatasource.api.remote.MovieApiService
import com.example.premiummovies.data.remotedatasource.utils.ResponseConverter
import com.example.premiummovies.data.repositoryimpl.MovieRepositoryImpl
import com.example.premiummovies.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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
    fun provideMovieRepository(
        movieApiService: MovieApiService,
        responseConverter: ResponseConverter,
        movieMapper: MovieMapper,
        genresMapper: GenresMapper,
        movieDetailsMapper: MovieDetailsMapper,
        appDataBase: AppDatabase,
        ioDispatcher: CoroutineDispatcher
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieApiService,
            responseConverter,
            genresMapper,
            movieMapper,
            appDataBase,
            movieDetailsMapper,
            ioDispatcher
        )
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