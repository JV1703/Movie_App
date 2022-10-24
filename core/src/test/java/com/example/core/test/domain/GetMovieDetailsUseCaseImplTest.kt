package com.example.core.test.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.data.source.Resource
import com.example.core.fake.data.repository.FakeMoviesRepository
import com.example.core.utils.DataDummy
import com.example.core.utils.MainCoroutineRule
import com.example.core.utils.Mapper
import com.example.details.domain.GetMovieDetailsUseCase
import com.example.details.domain.GetMovieDetailsUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieDetailsUseCaseImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var repository: FakeMoviesRepository
    private lateinit var useCase: GetMovieDetailsUseCase

    @Before
    fun setup() {
        repository = FakeMoviesRepository()
        useCase = GetMovieDetailsUseCaseImpl(repository)
    }

    @Test
    fun invoke_emitsResourceSuccess() = runTest {
        val result = useCase(0).first()
        assertTrue(result is Resource.Success)
        assertNotNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun invoke_emitsResourceError() = runTest {
        repository.setIsSuccess(false)
        val result = useCase(0).first()
        assertTrue(result is Resource.Error)
        assertNull(result.data)
        assertNotNull(result.message)
    }

    @Test
    fun invoke_emitsResourceEmpty() = runTest {
        repository.setIsBodyEmpty(true)
        val result = useCase(0).first()
        assertTrue(result is Resource.Empty)
        assertNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun saveMovie_MovieDetails() = runTest {
        val model = DataDummy.generateMovieDetails()
        useCase.saveMovie(model)
        val savedMovies = useCase.getSavedMovies().first()
        assertTrue(savedMovies.isNotEmpty())
        assertTrue(savedMovies.size == 1)
    }

    @Test
    fun saveMovie_SavedMovie() = runTest {
        val model = DataDummy.generateSavedMovie()
        useCase.saveMovie(model)
        val savedMovies = useCase.getSavedMovies().first()
        assertTrue(savedMovies.isNotEmpty())
        assertTrue(savedMovies.size == 1)
    }

    @Test
    fun deleteSavedMovie() = runTest {
        repeat(3) {
            useCase.saveMovie(DataDummy.generateSavedMovie())
        }
        val savedMovies = repository.getSavedMovies().first()
        useCase.deleteSavedMovie(0)
        assertTrue(savedMovies.none { it.id == 2 })
    }

    @Test
    fun getSavedMovie() = runTest {
        repeat(3) {
            useCase.saveMovie(DataDummy.generateSavedMovie())
        }
        val savedMovies = useCase.getSavedMovies().first()
        assertTrue(savedMovies.size == 3)
    }

    @Test
    fun getSavedMovieById_emitsSavedMovie() = runTest {
        val model = Mapper.toSavedMovie(DataDummy.generateSavedMovieEntity(1)[0])
        useCase.saveMovie(model)
        val savedMovie = useCase.getSavedMovieById(1).first()
        assertEquals(1, savedMovie?.id)
    }

    @Test
    fun getSavedMovieById_emitsNull() = runTest {
        val model = Mapper.toSavedMovie(DataDummy.generateSavedMovieEntity(1)[0])
        useCase.saveMovie(model)
        val savedMovie = useCase.getSavedMovieById(0).first()
        assertEquals(null, savedMovie?.id)
    }

    @Test
    fun isMovieSaved_emitsTrue() = runTest {
        val model = Mapper.toSavedMovie(DataDummy.generateSavedMovieEntity(1)[0])
        useCase.saveMovie(model)
        val savedMovie = useCase.isMovieSaved(1).first()
        assertEquals(true, savedMovie)
    }

    @Test
    fun isMovieSaved_emitsFalse() = runTest {
        val model = Mapper.toSavedMovie(DataDummy.generateSavedMovieEntity(1)[0])
        useCase.saveMovie(model)
        val savedMovie = useCase.isMovieSaved(0).first()
        assertEquals(false, savedMovie)
    }

}