package com.example.premiummovies.di


import android.content.Context
import com.example.premiummovies.presentation.utils.ResourcesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Dependency injection class that provides instances related to UI utils
 */

@Module
@InstallIn(SingletonComponent::class)
object UiModule {
    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourcesProvider {
        return ResourcesProvider(context)
    }
}