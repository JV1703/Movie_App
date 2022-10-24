package com.example.core.test.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.fake.data.usecase.FakeMovieDetailsUseCase
import com.example.core.utils.DataDummy
import com.example.core.utils.MainCoroutineRule
import com.example.details.presentation.details.DetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var useCase: FakeMovieDetailsUseCase
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        useCase = FakeMovieDetailsUseCase()
        viewModel = DetailsViewModel(useCase)
    }

    @Test
    fun uiState_emitsDataWithBookmarkEqualsTrue() = runTest {
        viewModel.saveMovie(DataDummy.generateMovieDetails())
        val collectJob = launch(Dispatchers.Main) {
            viewModel.uiState.collect()
        }
        val data = viewModel.uiState.value
        assertNotNull(data.details)
        assertEquals(true, data.isBookmarked)
        collectJob.cancel()
    }

    @Test
    fun uiState_emitsDataWithBookmarkEqualsFalse() = runTest {
        viewModel.saveMovie(DataDummy.generateMovieDetails())
        viewModel.saveMovieId(100)
        val collectJob = launch(Dispatchers.Main) {
            viewModel.uiState.collect()
        }
        val data = viewModel.uiState.value
        assertNotNull(data.details)
        assertEquals(false, data.isBookmarked)
        assertNull(data.userMessage)
        collectJob.cancel()
    }

    @Test
    fun uiState_emitsNullDataWithUserMessage() = runTest {
        useCase.setIsSuccess(false)
        viewModel.saveMovie(DataDummy.generateMovieDetails())
        val collectJob = launch(Dispatchers.Main) {
            viewModel.uiState.collect()
        }
        val data = viewModel.uiState.value
        assertNull(data.details)
        assertNotNull(data.userMessage)
        collectJob.cancel()
    }
}