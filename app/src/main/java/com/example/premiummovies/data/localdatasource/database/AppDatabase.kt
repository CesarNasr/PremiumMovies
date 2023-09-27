package com.example.premiummovies.data.localdatasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.premiummovies.data.localdatasource.dao.GenresDao
import com.example.premiummovies.data.localdatasource.dao.MoviesDao
import com.example.premiummovies.data.localdatasource.entity.genre.GenreDataEntity
import com.example.premiummovies.data.localdatasource.entity.movies.MovieListEntity

const val moviesDatabaseName = "movies.db"

@Database(
    entities = [MovieListEntity::class, GenreDataEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MovieDataTypeConverter::class, IntegerTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun MoviesDao(): MoviesDao
    abstract fun GenresDao(): GenresDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, moviesDatabaseName
        )
            .build()
    }
}
