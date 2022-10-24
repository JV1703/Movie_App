package com.example.core.data.repository

import com.example.core.data.source.Resource
import com.example.core.data.source.remote.response.search.SearchResponse
import com.example.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPopularMovies(
        language: String? = null, page: Int? = null, region: String? = null
    ): Flow<Resource<List<Movies>>>

    fun getTrendingMovies(timeWindow: String): Flow<Resource<List<Movies>>>

    fun getNowPlaying(
        language: String? = null, page: Int? = null, region: String? = null
    ): Flow<Resource<List<Movies>>>

    suspend fun searchMovies(
        query: String,
        language: String? = null,
        page: Int? = null,
        includeAdult: Boolean? = null,
        region: String? = null,
        year: Int? = null,
        primaryReleaseYear: Int? = null
    ): Resource<SearchResponse>

    fun getMovieDetails(
        movieId: Int,
        language: String? = null,
        appendToResponse: String? = "videos,recommendations,credits"
    ): Flow<Resource<MovieDetails>>

    suspend fun saveMovie(movieDetails: MovieDetails)

    fun getSavedMovies(): Flow<List<SavedMovie>>

    suspend fun deleteSavedMovie(id: Int)

    suspend fun saveMovie(savedMovie: SavedMovie)

    fun getSavedMovieById(id: Int): Flow<SavedMovie?>
}