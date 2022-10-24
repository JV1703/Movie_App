package com.example.core.test.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.data.source.local.MoviesLocalDataSource
import com.example.core.data.source.local.MoviesLocalDataSourceImpl
import com.example.core.fake.data.source.FakeDao
import com.example.core.utils.DataDummy
import com.example.core.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesLocalDataSourceImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var fakeDao: FakeDao
    private lateinit var local: MoviesLocalDataSource

    @Before
    fun setup() {
        fakeDao = FakeDao()
        local = MoviesLocalDataSourceImpl(fakeDao)
    }

    @Test
    fun saveMovie_retrieveSavedMovie() = runTest {
        local.saveMovie(DataDummy.generateSavedMovieEntity(1)[0])
        val data = local.getSavedMovies().first()
        assertTrue(data.contains(DataDummy.generateSavedMovieEntity(1)[0]))
    }

    @Test
    fun getSavedMovies_retrieveAllSavedMovies() = runTest {
        val data = DataDummy.generateSavedMovieEntity(3)
        for (movie in data) {
            local.saveMovie(movie)
        }
        val savedMovies = local.getSavedMovies().first()
        assertTrue(savedMovies.size == 3)
    }

    @Test
    fun deleteSavedMovie_unableToRetrieveDeletedMovie() = runTest {
        val data = DataDummy.generateSavedMovieEntity(3)
        for (movie in data) {
            local.saveMovie(movie)
        }
        val savedMovies = local.getSavedMovies().first()
        local.deleteSavedMovie(3)
        assertTrue(savedMovies.none { it.id == 3 })
    }

    @Test
    fun getSavedMovieById_emitsSavedMovieEntity() = runTest {
        val model = DataDummy.generateSavedMovieEntity(1)[0]
        local.saveMovie(model)
        val retrievedData = local.getSavedMovieById(1).first()
        assertEquals(1, retrievedData?.id)
    }

    @Test
    fun getSavedMovieById_emitsNull() = runTest {
        val model = DataDummy.generateSavedMovieEntity(1)[0]
        local.saveMovie(model)
        val retrievedData = local.getSavedMovieById(2).first()
        assertTrue(retrievedData?.id != 1)
    }
}