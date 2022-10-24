package com.example.core.fake.data.source

import com.example.core.utils.DataDummy
import com.example.core.data.source.remote.MoviesRemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.apiResponseHandler
import com.example.core.data.source.remote.response.movie_details.MovieDetailsResponse
import com.example.core.data.source.remote.response.now_playing.NowPlayingResponse
import com.example.core.data.source.remote.response.popular_movies.PopularMoviesResponse
import com.example.core.data.source.remote.response.search.SearchResponse
import com.example.core.data.source.remote.response.trending_movies.TrendingMoviesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeMoviesRemoteDataSource:MoviesRemoteDataSource {

    private var isSuccess = true
    private var isBodyEmpty = false

    fun setIsSuccess(isSuccess: Boolean){
        this@FakeMoviesRemoteDataSource.isSuccess = isSuccess
    }

    fun setIsBodyEmpty(isEmpty: Boolean){
        isBodyEmpty = isEmpty
    }

    override fun getPopularMovies(
        language: String?,
        page: Int?,
        region: String?
    ): Flow<ApiResponse<PopularMoviesResponse>> {
        val model = DataDummy.generatePopularMoviesResponse()
        val response = DataDummy.generateResponse(isSuccess, isBodyEmpty) { Response.success(model) }
        return flow{
            emit(
                apiResponseHandler(response)
            )
        }
    }

    override fun getTrendingMovies(timeWindow: String): Flow<ApiResponse<TrendingMoviesResponse>> {
        val model = DataDummy.generateTrendingMoviesResponse()
        val response = DataDummy.generateResponse(isSuccess, isBodyEmpty) { Response.success(model) }
        return flow{
            emit(
                apiResponseHandler(response)
            )
        }
    }

    override fun getPlayingNow(
        language: String?,
        page: Int?,
        region: String?
    ): Flow<ApiResponse<NowPlayingResponse>> {
        val model = DataDummy.generateNowPlayingResponse()
        val response = DataDummy.generateResponse(isSuccess, isBodyEmpty) { Response.success(model) }
        return flow{
            emit(
                apiResponseHandler(response)
            )
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
        val model = DataDummy.generateSearchMovieResponse()
        return DataDummy.generateResponse(isSuccess, isBodyEmpty) { Response.success(model) }
    }

    override fun getMovieDetails(
        movieId: Int,
        language: String?,
        appendToResponse: String?
    ): Flow<ApiResponse<MovieDetailsResponse>> {
        // will be using mock for this
        val model = DataDummy.generateMovieDetailsResponse()
        val response = DataDummy.generateResponse(isSuccess, isBodyEmpty) { Response.success(model) }
        return flow{
            emit(
                apiResponseHandler(response)
            )
        }
    }
}