package com.example.movieapp.domain

import com.example.core.data.source.Resource
import com.example.core.domain.model.Movies
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getNowPlaying(
        language: String? = null, page: Int? = null, region: String? = null
    ): Flow<Resource<List<Movies>>>

    fun getPopularMovies(
        language: String? = null, page: Int? = null, region: String? = null
    ): Flow<Resource<List<Movies>>>

    fun getTrendingMovies(timeWindow: String): Flow<Resource<List<Movies>>>


}