package com.example.movieapp.test.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.apiResponseHandler
import com.example.core.data.source.remote.response.search.SearchResponse
import com.example.core.data.source.resourceHandler
import com.example.movieapp.fake.data.repository.FakeMoviesRepository
import com.example.movieapp.utils.DataDummy
import com.example.movieapp.utils.MainCoroutineRule
import com.example.core.utils.Mapper
import com.example.search.domain.SearchMoviesPagingSource
import com.example.search.domain.usecase.SearchMoviesUseCase
import com.example.search.domain.usecase.SearchMoviesUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class SearchMovieUseCaseImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var repository: FakeMoviesRepository
    private lateinit var useCase: SearchMoviesUseCase

    @Before
    fun setup() {
        repository = FakeMoviesRepository()
        useCase = SearchMoviesUseCaseImpl(repository)
    }

    @Test
    fun invoke_emitPagingDataSuccessLoadMoreData() = runTest {

        val searchResponsePage1 = SearchResponse(
            page = 1,
            results = DataDummy.generateSearchResult(),
            totalPages = 10,
            totalResults = 100
        )
        val searchResponsePage2 = SearchResponse(
            page = 2,
            results = DataDummy.generateSearchResult(),
            totalPages = 10,
            totalResults = 100
        )
        val searchResponsePage3 = SearchResponse(
            page = 3,
            results = DataDummy.generateSearchResult(),
            totalPages = 10,
            totalResults = 100
        )
        val searchResponseList =
            listOf(searchResponsePage1, searchResponsePage2, searchResponsePage3)
        val resourceList = searchResponseList.map {
            DataDummy.generateResponse(isSuccess = true) { Response.success(it) }
        }.map {
            apiResponseHandler(it)
        }.map { resourceHandler(it) { (it as ApiResponse.Success).data } }

        val data = resourceList[0].data?.results ?: emptyList()

        val paging = SearchMoviesPagingSource("", repository)

        val expected = PagingSource.LoadResult.Page(
            data = data.map { Mapper.toSearchMovies(it) },
            prevKey = null,
            nextKey = resourceList[1].data?.page
        )
        val actual = paging.load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = false
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun invoke_emitPagingDataError() = runTest {

        repository.setIsSuccess(false)

        val searchResponsePage = SearchResponse(
            page = 1,
            results = DataDummy.generateSearchResult(),
            totalPages = 10,
            totalResults = 100
        )

        val searchResponseList = listOf(searchResponsePage)

        val resourceList = searchResponseList.map {
            DataDummy.generateResponse(isSuccess = false) { Response.success(it) }
        }.map {
            apiResponseHandler(it)
        }.map { resourceHandler(it) { (it as ApiResponse.Success).data } }

        val data = resourceList[0].data?.results ?: emptyList()

        val paging = SearchMoviesPagingSource("", repository)

        val expected = PagingSource.LoadResult.Page(
            data = data.map { Mapper.toSearchMovies(it) },
            prevKey = null,
            nextKey = resourceList[0].data?.page
        )
        val actual = paging.load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = false
            )
        )

        assertEquals(expected, actual)
    }
}