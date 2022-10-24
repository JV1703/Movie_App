package com.example.core.data.source.remote

import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.movie_details.MovieDetailsResponse
import com.example.core.data.source.remote.response.now_playing.NowPlayingResponse
import com.example.core.data.source.remote.response.popular_movies.PopularMoviesResponse
import com.example.core.data.source.remote.response.search.SearchResponse
import com.example.core.data.source.remote.response.trending_movies.TrendingMoviesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MoviesRemoteDataSource {
    fun getPopularMovies(
        language: String? = null, page: Int? = null, region: String? = null
    ): Flow<ApiResponse<PopularMoviesResponse>>

    fun getTrendingMovies(
        timeWindow: String
    ): Flow<ApiResponse<TrendingMoviesResponse>>

    fun getPlayingNow(
        language: String? = null, page: Int? = null, region: String? = null
    ): Flow<ApiResponse<NowPlayingResponse>>

    suspend fun searchMovies(
        query: String,
        language: String? = null,
        page: Int? = null,
        includeAdult: Boolean? = null,
        region: String? = null,
        year: Int? = null,
        primaryReleaseYear: Int? = null
    ): Response<SearchResponse>

    fun getMovieDetails(
        movieId: Int,
        language: String? = null,
        appendToResponse: String? = "videos,recommendations,credits"
    ): Flow<ApiResponse<MovieDetailsResponse>>
}