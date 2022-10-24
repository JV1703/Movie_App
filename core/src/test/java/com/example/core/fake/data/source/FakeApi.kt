package com.example.core.fake.data.source

import com.example.core.utils.DataDummy
import com.example.core.utils.DataDummy.generateResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.movie_details.MovieDetailsResponse
import com.example.core.data.source.remote.response.now_playing.NowPlayingResponse
import com.example.core.data.source.remote.response.popular_movies.PopularMoviesResponse
import com.example.core.data.source.remote.response.search.SearchResponse
import com.example.core.data.source.remote.response.trending_movies.TrendingMoviesResponse
import retrofit2.Response

class FakeApi : ApiService {

    private var isSuccess = true
    private var isBodyEmpty = false

    fun setIsSuccess(isSuccess: Boolean) {
        this@FakeApi.isSuccess = isSuccess
    }

    fun setIsBodyEmpty(isEmpty: Boolean) {
        isBodyEmpty = isEmpty
    }

    override suspend fun getPopularMovies(
        language: String?, page: Int?, region: String?
    ): Response<PopularMoviesResponse> {
        val response = DataDummy.generatePopularMoviesResponse()
        return generateResponse(isSuccess, isBodyEmpty) {
            Response.success(response)
        }
    }

    override suspend fun getTrendingMovies(timeWindow: String): Response<TrendingMoviesResponse> {
        val response = DataDummy.generateTrendingMoviesResponse()
        return generateResponse(isSuccess, isBodyEmpty) {
            Response.success(response)
        }
    }

    override suspend fun searchMovies(
        query: String,
        language: String?,
        page: Int?,
        includeAdult: Boolean?,
        region: String?,
        year: Int?,
        primaryReleaseYear: Int?
    ): Response<SearchResponse> {
        val response = DataDummy.generateSearchMovieResponse()
        return generateResponse(isSuccess, isBodyEmpty) {
            Response.success(response)
        }
    }

    override suspend fun getMovieDetails(
        movieId: Int, language: String?, appendToResponse: String?
    ): Response<MovieDetailsResponse> {
        val response = DataDummy.generateMovieDetailsResponse()
        return generateResponse(isSuccess, isBodyEmpty) {
            Response.success(response)
        }
    }

    override suspend fun getNowPlaying(
        language: String?, page: Int?, region: String?
    ): Response<NowPlayingResponse> {
        val response = DataDummy.generateNowPlayingResponse()
        return generateResponse(isSuccess, isBodyEmpty) {
            Response.success(response)
        }
    }

}