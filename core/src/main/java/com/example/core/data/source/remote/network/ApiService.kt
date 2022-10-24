package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.movie_details.MovieDetailsResponse
import com.example.core.data.source.remote.response.now_playing.NowPlayingResponse
import com.example.core.data.source.remote.response.popular_movies.PopularMoviesResponse
import com.example.core.data.source.remote.response.search.SearchResponse
import com.example.core.data.source.remote.response.trending_movies.TrendingMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("3/movie/now_playing")
    suspend fun getNowPlaying(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
        @Query("region") region: String? = null
    ): Response<NowPlayingResponse>

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
        @Query("region") region: String? = null
    ): Response<PopularMoviesResponse>

    @GET("3/trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String
    ): Response<TrendingMoviesResponse>

    @GET("3/search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("include_adult") includeAdult: Boolean?,
        @Query("region") region: String?,
        @Query("year") year: Int?,
        @Query("primary_release_year") primaryReleaseYear: Int?
    ): Response<SearchResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null,
        @Query("append_to_response") appendToResponse: String? = "videos,recommendations,credits"
    ): Response<MovieDetailsResponse>

}