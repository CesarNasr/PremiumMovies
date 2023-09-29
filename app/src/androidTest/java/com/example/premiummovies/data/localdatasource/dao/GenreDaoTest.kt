package com.example.premiummovies.data.localdatasource.dao


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.premiummovies.data.localdatasource.database.AppDatabase
import com.example.premiummovies.data.localdatasource.entity.genre.GenreDataEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class GenreDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: GenresDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.GenresDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertGenresItems() = runTest {
        val genreData = mutableListOf<GenreDataEntity>()
        for(i in 0 until 10){
            genreData.add(GenreDataEntity(i, "genre $i"))
        }

        dao.insertItems(genreData)

        val allItems = dao.getAll()

        assertThat(allItems).contains(genreData[1])
        assertThat(allItems).hasSize(9)
    }

    @Test
    fun deleteGenreItems() = runTest {
        val genreData = mutableListOf<GenreDataEntity>()
        for(i in 0 until 10){
            genreData.add(GenreDataEntity(i, "genre $i"))
        }

        dao.insertItems(genreData)

        val allItems = dao.getAll()
        assertThat(allItems).hasSize(9)

        dao.deleteAllItems()
        val deleteItems = dao.getAll()
        assertThat(deleteItems).hasSize(0)
    }


    @Test
    fun insertGenreData() = runTest {
        val genreData = mutableListOf<GenreDataEntity>()
        for(i in 0 until 20){
            genreData.add(GenreDataEntity(i, "genre $i"))
        }
        dao.insertItems(genreData)

        val allItems = dao.getAll()
        assertThat(allItems).hasSize(19)


        val newGenreData = mutableListOf<GenreDataEntity>()
        for(i in 20 until 30){
            newGenreData.add(GenreDataEntity(i, "genre $i"))
        }

        dao.insertGenresData(newGenreData)
        val newItems = dao.getAll()
        assertThat(newItems).hasSize(10)
    }

}













