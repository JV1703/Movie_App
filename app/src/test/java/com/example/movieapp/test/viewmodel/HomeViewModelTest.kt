package com.example.movieapp.test.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.domain.model.Movies
import com.example.movieapp.fake.data.usecase.FakeMovieUseCase
import com.example.movieapp.utils.MainCoroutineRule
import com.example.movieapp.presentation.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var useCase: FakeMovieUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        useCase = FakeMovieUseCase()
        viewModel = HomeViewModel(useCase)
    }

    @Test
    fun getMovies_emitUiStateWithDataAndNullMessage() = runTest {
        viewModel.getMovies(timeWindow = "")
        val result = viewModel.homeUiState.value
        assertTrue(!result.isLoading)
        assertNotEquals("", result.nowPlayingMovies)
        assertNotEquals("", result.popularMovies)
        assertNotEquals("", result.trendingMovies)
        assertNull(result.nowPlayingMsg)
        assertNull(result.popularMoviesMsg)
        assertNull(result.trendingMoviesMsg)
    }

    @Test
    fun getMovies_emitUiStateWithEmptyData() = runTest {
        useCase.setIsSuccess(false)
        viewModel.getMovies(timeWindow = "")
        val result = viewModel.homeUiState.value
        assertTrue(!result.isLoading)
        assertEquals(emptyList<Movies>(), result.nowPlayingMovies)
        assertEquals(emptyList<Movies>(), result.popularMovies)
        assertEquals(emptyList<Movies>(), result.trendingMovies)
        assertNotNull(result.nowPlayingMsg)
        assertNotNull(result.popularMoviesMsg)
        assertNotNull(result.trendingMoviesMsg)
    }

}