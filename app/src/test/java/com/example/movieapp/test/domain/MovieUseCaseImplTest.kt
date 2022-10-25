package com.example.movieapp.test.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.data.source.Resource
import com.example.core.domain.model.Movies
import com.example.movieapp.fake.data.repository.FakeMoviesRepository
import com.example.movieapp.utils.MainCoroutineRule
import com.example.movieapp.domain.MovieUseCase
import com.example.movieapp.domain.MovieUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class MovieUseCaseImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var repository: FakeMoviesRepository
    private lateinit var useCase: MovieUseCase

    @Before
    fun setup() {
        repository = FakeMoviesRepository()
        useCase = MovieUseCaseImpl(repository)
    }

    @Test
    fun getNowPlaying_emitResourceSuccess() = runTest {
        repository.setIsSuccess(true)
        val result = useCase.getNowPlaying().first()
        assertTrue(result is Resource.Success)
        assertTrue(result.data is List<Movies>)
        assertNotNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getNowPlaying_emitApiResourceError() = runTest {
        repository.setIsSuccess(false)
        val result = useCase.getNowPlaying().first()
        assertTrue(result is Resource.Error)
        assertNull(result.data)
        assertNotNull(result.message)
    }

    @Test
    fun getNowPlaying_emitApiResourceEmpty() = runTest {
        repository.setIsBodyEmpty(true)
        val result = useCase.getNowPlaying().first()
        assertTrue(result is Resource.Empty)
        assertNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getPopularMovies_emitApiResourceSuccess() = runTest {
        repository.setIsSuccess(true)
        val result = useCase.getPopularMovies().first()
        assertTrue(result is Resource.Success)
        assertTrue(result.data is List<Movies>)
        assertNotNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getPopularMovies_emitApiResourceError() = runTest {
        repository.setIsSuccess(false)
        val result = useCase.getPopularMovies().first()
        assertTrue(result is Resource.Error)
        assertNull(result.data)
        assertNotNull(result.message)
    }

    @Test
    fun getPopularMovies_emitApiResourceEmpty() = runTest {
        repository.setIsBodyEmpty(true)
        val result = useCase.getPopularMovies().first()
        assertTrue(result is Resource.Empty)
        assertNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getTrendingMovies_emitApiResourceSuccess() = runTest {
        repository.setIsBodyEmpty(false)
        val result = useCase.getTrendingMovies("day").first()
        assertTrue(result is Resource.Success)
        assertTrue(result.data is List<Movies>)
        assertNotNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getTrendingMovies_emitApiResourceError() = runTest {
        repository.setIsSuccess(false)
        val result = useCase.getTrendingMovies("day").first()
        assertTrue(result is Resource.Error)
        assertNull(result.data)
        assertNotNull(result.message)
    }

    @Test
    fun getTrendingMovies_emitApiResponseError() = runTest {
        repository.setIsSuccess(false)
        val result = useCase.getTrendingMovies("day").first()
        assertTrue(result is Resource.Error)
        assertNull(result.data)
        assertNotNull(result.message)
    }

}