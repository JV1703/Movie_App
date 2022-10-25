package com.example.movieapp.test.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.data.repository.MoviesRepository
import com.example.core.data.repository.MoviesRepositoryImpl
import com.example.core.data.source.Resource
import com.example.movieapp.fake.data.source.FakeMoviesLocalDataSource
import com.example.movieapp.fake.data.source.FakeMoviesRemoteDataSource
import com.example.movieapp.utils.DataDummy
import com.example.movieapp.utils.MainCoroutineRule
import com.example.core.utils.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var fakeRemote: FakeMoviesRemoteDataSource
    private lateinit var fakeLocal: FakeMoviesLocalDataSource
    private lateinit var repo: MoviesRepository

    @Before
    fun setup() {
        fakeRemote = FakeMoviesRemoteDataSource()
        fakeLocal = FakeMoviesLocalDataSource()
        repo = MoviesRepositoryImpl(fakeRemote, fakeLocal, Dispatchers.Main)
    }

    @Test
    fun getPopularMovies_emitResourceSuccess() = runTest {
        val result = repo.getPopularMovies().first()
        assertTrue(result is Resource.Success)
        assertNotNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getPopularMovies_emitResourceError() = runTest {
        fakeRemote.setIsSuccess(false)
        val result = repo.getPopularMovies().first()
        assertTrue(result is Resource.Error)
        assertNotNull(result.message)
        assertNull(result.data)
    }

    @Test
    fun getPopularMovies_emitResourceEmpty() = runTest {
        fakeRemote.setIsBodyEmpty(true)
        val result = repo.getPopularMovies().first()
        assertTrue(result is Resource.Empty)
        assertNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getTrendingMovies_emitResourceSuccess() = runTest {
        val result = repo.getTrendingMovies("").first()
        assertTrue(result is Resource.Success)
        assertNotNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getTrendingMovies_emitResourceError() = runTest {
        fakeRemote.setIsSuccess(false)
        val result = repo.getTrendingMovies("").first()
        assertTrue(result is Resource.Error)
        assertNull(result.data)
        assertNotNull(result.message)
    }

    @Test
    fun getTrendingMovies_emitResourceEmpty() = runTest {
        fakeRemote.setIsBodyEmpty(true)
        val result = repo.getTrendingMovies("").first()
        assertTrue(result is Resource.Empty)
        assertNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getNowPlaying_emitResourceSuccess() = runTest {
        val result = repo.getNowPlaying().first()
        assertTrue(result is Resource.Success)
        assertNotNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getNowPlaying_emitResourceError() = runTest {
        fakeRemote.setIsSuccess(false)
        val result = repo.getNowPlaying().first()
        assertTrue(result is Resource.Error)
        assertNull(result.data)
        assertNotNull(result.message)
    }

    @Test
    fun getNowPlaying_emitResourceEmpty() = runTest {
        fakeRemote.setIsBodyEmpty(true)
        val result = repo.getNowPlaying().first()
        assertTrue(result is Resource.Empty)
        assertNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun searchMovies_emitResourceSuccess() = runTest {
        val result = repo.searchMovies("")
        assertTrue(result is Resource.Success)
        assertNotNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun searchMovies_emitResourceError() = runTest {
        fakeRemote.setIsSuccess(false)
        val result = repo.searchMovies("")
        assertTrue(result is Resource.Error)
        assertNull(result.data)
        assertNotNull(result.message)
    }

    @Test
    fun searchMovies_emitResourceEmpty() = runTest {
        fakeRemote.setIsBodyEmpty(true)
        val result = repo.searchMovies("")
        assertTrue(result is Resource.Empty)
        assertNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getMovieDetails_emitResourceSuccess() = runTest {
        val result = repo.getMovieDetails(0).first()
        assertTrue(result is Resource.Success)
        assertNotNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun getMovieDetails_emitResourceError() = runTest {
        fakeRemote.setIsSuccess(false)
        val result = repo.getMovieDetails(0).first()
        assertTrue(result is Resource.Error)
        assertNull(result.data)
        assertNotNull(result.message)
    }

    @Test
    fun getMovieDetails_emitResourceEmpty() = runTest {
        fakeRemote.setIsBodyEmpty(true)
        val result = repo.getMovieDetails(0).first()
        assertTrue(result is Resource.Empty)
        assertNull(result.data)
        assertNull(result.message)
    }

    @Test
    fun saveMovie() = runTest {
        val model = DataDummy.generateSavedMovieEntity(1)[0]
        repo.saveMovie(Mapper.toSavedMovie(model))
        val savedMovies = repo.getSavedMovies().first()
        assertTrue(savedMovies.size == 1)
        assertEquals(Mapper.toSavedMovie(model), savedMovies[0])
    }

    @Test
    fun getSavedMovies() = runTest {
        val movieList = DataDummy.generateSavedMovieEntity(3)
        for (movie in movieList) {
            repo.saveMovie(Mapper.toSavedMovie(movie))
        }
        val savedMovies = repo.getSavedMovies().first()
        assertTrue(savedMovies.size == 3)
    }

    @Test
    fun deleteSavedMovie() = runTest {
        val movieList = DataDummy.generateSavedMovieEntity(3)
        for (movie in movieList) {
            repo.saveMovie(Mapper.toSavedMovie(movie))
        }
        repo.deleteSavedMovie(3)
        val savedMovies = repo.getSavedMovies().first()
        assertTrue(savedMovies.none { it.id == 3 })
    }

    @Test
    fun getSavedMovieById_emitsSavedMovie() = runTest {
        val model = DataDummy.generateSavedMovieEntity(1)
        repo.saveMovie(Mapper.toSavedMovie(model[0]))
        val savedMovies = repo.getSavedMovies().first()
        assertEquals(1, savedMovies.size)
        assertEquals(1, savedMovies[0].id)
    }

    @Test
    fun getSavedMovieById_emitsSavedMovieDoesNotExist() = runTest {
        val model = DataDummy.generateSavedMovieEntity(1)
        repo.saveMovie(Mapper.toSavedMovie(model[0]))
        val savedMovies = repo.getSavedMovies().first()
        assertEquals(1, savedMovies.size)
        assertNotEquals(2, savedMovies[0].id)
    }
}