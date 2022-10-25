package com.example.movieapp.test.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bookmark.domain.SaveMoviesUseCase
import com.example.bookmark.domain.SaveMoviesUseCaseImpl
import com.example.movieapp.utils.DataDummy
import com.example.movieapp.fake.data.repository.FakeMoviesRepository
import com.example.movieapp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveMovieUseCaseImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var repository: FakeMoviesRepository
    private lateinit var useCase: SaveMoviesUseCase

    @Before
    fun setup() {
        repository = FakeMoviesRepository()
        useCase = SaveMoviesUseCaseImpl(repository)
    }

    @Test
    fun saveMovie() = runTest {
        val model = DataDummy.generateSavedMovie()
        useCase.saveMovie(model)
        val savedMovies = useCase.getSavedMovies().first()
        assertTrue(savedMovies.size == 1)
        assertTrue(savedMovies.contains(model))
    }

    @Test
    fun saveMovie_movieDetails() = runTest {
        val model = DataDummy.generateMovieDetails()
        useCase.saveMovie(model)
        val savedMovies = useCase.getSavedMovies().first()
        assertTrue(savedMovies.size == 1)
    }

    @Test
    fun getSavedMovies() = runTest {
        val model = DataDummy.generateSavedMovie()
        repeat(3) {
            useCase.saveMovie(model)
        }
        val savedMovies = useCase.getSavedMovies().first()
        assertTrue(savedMovies.size == 3)
    }

    @Test
    fun deleteSavedMovies() = runTest {
        val model = DataDummy.generateSavedMovie()
        repeat(3) {
            useCase.saveMovie(model)
        }
        useCase.deleteSavedMovie(0)
        val savedMovies = useCase.getSavedMovies().first()
        assertTrue(savedMovies.size == 2)
    }
}