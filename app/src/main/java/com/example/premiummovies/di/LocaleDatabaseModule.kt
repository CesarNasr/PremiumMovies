/*
package com.example.premiummovies.di

import android.content.Context
import androidx.room.Room
import com.example.premiummovies.data.localdatasource.database.AppDatabase
import com.example.premiummovies.data.localdatasource.database.moviesDatabaseName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

*/
/**
 * Dependency injection class that provides instances related to local room db
 *//*

@Module
@InstallIn(SingletonComponent::class)
object LocaleDatabaseModule {

    @Provides
    @Singleton
    fun provideRoomInstance(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, moviesDatabaseName
        ).build()
    }
}*/
