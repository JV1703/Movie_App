package com.example.search.domain.usecase

import androidx.paging.PagingData
import com.example.core.domain.model.SearchMovies
import kotlinx.coroutines.flow.Flow

interface SearchMoviesUseCase {
    operator fun invoke(
        query: String,
        language: String? = null,
        page: Int? = null,
        includeAdult: Boolean? = null,
        region: String? = null,
        year: Int? = null,
        primaryReleaseYear: Int? = null
    ): Flow<PagingData<SearchMovies>>
}