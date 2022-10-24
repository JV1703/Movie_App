package com.example.search.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.repository.MoviesRepository
import com.example.core.domain.model.SearchMovies
import com.example.search.domain.SearchMoviesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCaseImpl @Inject constructor(private val moviesRepository: MoviesRepository) :
    SearchMoviesUseCase {

    override fun invoke(
        query: String,
        language: String?,
        page: Int?,
        includeAdult: Boolean?,
        region: String?,
        year: Int?,
        primaryReleaseYear: Int?
    ): Flow<PagingData<SearchMovies>> = Pager(config = PagingConfig(10),
        pagingSourceFactory = { SearchMoviesPagingSource(query, moviesRepository) }).flow

}