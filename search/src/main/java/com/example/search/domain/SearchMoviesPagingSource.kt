package com.example.search.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.repository.MoviesRepository
import com.example.core.data.source.Resource
import com.example.core.domain.model.SearchMovies
import com.example.core.utils.Mapper
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class SearchMoviesPagingSource(
    private val query: String, private val repository: MoviesRepository
) : PagingSource<Int, SearchMovies>() {

    override fun getRefreshKey(state: PagingState<Int, SearchMovies>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchMovies> {
        val page = params.key ?: 1

        return try {
            val response = repository.searchMovies(query, page = page)

            if (response is Resource.Error) {
                Timber.e(response.message)
            }

            val nextKey = when (response) {
                is Resource.Success -> {
                    if (page != response.data!!.totalPages && response.data!!.totalPages != 0) page + 1 else null
                }
                else -> null
            }

            LoadResult.Page(data = response.data?.results?.map { result ->
                Mapper.toSearchMovies(
                    result
                )
            } ?: emptyList(), prevKey = if (page == 1) null else page - 1, nextKey = nextKey)

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}