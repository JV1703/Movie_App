package com.example.core.test.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.data.source.remote.MoviesRemoteDataSource
import com.example.core.data.source.remote.MoviesRemoteDataSourceImpl
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.fake.data.source.FakeApi
import com.example.core.utils.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class MoviesRemoteDataSourceImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var fakeApi: FakeApi
    private lateinit var remote: MoviesRemoteDataSource

    @Before
    fun setup() {
        fakeApi = FakeApi()
        remote = MoviesRemoteDataSourceImpl(fakeApi, Dispatchers.Main)
    }

    @Test
    fun getPopularMovies_emitApiResponseSuccess() = runTest {
        fakeApi.setIsSuccess(true)
        val actualResult = remote.getPopularMovies().first()
        assertTrue(actualResult is ApiResponse.Success)
        assertNotNull((actualResult as ApiResponse.Success).data)
    }

    @Test
    fun getPopularMovies_emitApiResponseError() = runTest {
        fakeApi.setIsSuccess(false)
        val actualResult = remote.getPopularMovies().first()
        assertTrue(actualResult is ApiResponse.Error)
        assertNotNull((actualResult as ApiResponse.Error).errorMessage)
    }

    @Test
    fun getPopularMovies_emitApiResponseEmpty() = runTest {
        fakeApi.setIsBodyEmpty(true)
        val actualResult = remote.getPopularMovies().first()
        assertTrue(actualResult is ApiResponse.Empty)
    }

    @Test
    fun getTrendingMovies_emitApiResponseSuccess() = runTest {
        fakeApi.setIsSuccess(true)
        val actualResult = remote.getTrendingMovies("day").first()
        assertTrue(actualResult is ApiResponse.Success)
        assertNotNull((actualResult as ApiResponse.Success).data)
    }

    @Test
    fun getTrendingMovies_emitApiResponseError() = runTest {
        fakeApi.setIsSuccess(false)
        val actualResult = remote.getTrendingMovies("day").first()
        assertTrue(actualResult is ApiResponse.Error)
        assertNotNull((actualResult as ApiResponse.Error).errorMessage)
    }

    @Test
    fun getTrendingMovies_emitApiResponseEmpty() = runTest {
        fakeApi.setIsBodyEmpty(true)
        val actualResult = remote.getTrendingMovies("day").first()
        assertTrue(actualResult is ApiResponse.Empty)
    }

    @Test
    fun getPlayingNow_emitApiResponseSuccess() = runTest {
        fakeApi.setIsSuccess(true)
        val actualResult = remote.getPlayingNow().first()
        assertTrue(actualResult is ApiResponse.Success)
        assertNotNull((actualResult as ApiResponse.Success).data)
    }

    @Test
    fun getPlayingNow_emitApiResponseError() = runTest {
        fakeApi.setIsSuccess(false)
        val actualResult = remote.getPlayingNow("day").first()
        assertTrue(actualResult is ApiResponse.Error)
        assertNotNull((actualResult as ApiResponse.Error).errorMessage)
    }

    @Test
    fun getPlayingNow_emitApiResponseEmpty() = runTest {
        fakeApi.setIsBodyEmpty(true)
        val actualResult = remote.getPlayingNow("day").first()
        assertTrue(actualResult is ApiResponse.Empty)
    }

    @Test
    fun searchMovies_returnResponseSuccess() = runTest {
        fakeApi.setIsSuccess(true)
        val actualResult = remote.searchMovies("")
        assertNotNull(actualResult.body())
        assertNull(actualResult.errorBody())
    }

    @Test
    fun searchMovies_returnResponseError() = runTest {
        fakeApi.setIsSuccess(false)
        val actualResult = remote.searchMovies("")
        assertNull(actualResult.body())
        assertNotNull(actualResult.errorBody())
    }

    @Test
    fun searchMovies_returnResponseEmpty() = runTest {
        fakeApi.setIsBodyEmpty(true)
        val actualResult = remote.searchMovies("")
        assertNull(actualResult.body())
        assertNull(actualResult.errorBody())
    }

    @Test
    fun getMovieDetails_returnResponseSuccess() = runTest {
        fakeApi.setIsBodyEmpty(false)
        val actualResult = remote.getMovieDetails(0).first()
        assertTrue(actualResult is ApiResponse.Success)
        assertNotNull((actualResult as ApiResponse.Success).data)
    }

    @Test
    fun getMovieDetails_returnResponseError() = runTest {
        fakeApi.setIsSuccess(false)
        val actualResult = remote.getMovieDetails(0).first()
        assertTrue(actualResult is ApiResponse.Error)
        assertNotNull((actualResult as ApiResponse.Error).errorMessage)
    }

    @Test
    fun getMovieDetails_returnResponseEmpty() = runTest {
        fakeApi.setIsBodyEmpty(true)
        val actualResult = remote.getMovieDetails(0).first()
        assertTrue(actualResult is ApiResponse.Empty)
    }

}