package com.example.core.data.source.remote

import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.network.apiResponseHandler
import com.example.core.data.source.remote.response.movie_details.MovieDetailsResponse
import com.example.core.data.source.remote.response.popular_movies.PopularMoviesResponse
import com.example.core.data.source.remote.response.search.SearchResponse
import com.example.core.di.CoroutinesQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val api: ApiService,
    @CoroutinesQualifiers.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MoviesRemoteDataSource {

    override fun getPopularMovies(
        language: String?, page: Int?, region: String?
    ): Flow<ApiResponse<PopularMoviesResponse>> = flow {
        val response = apiResponseHandler(api.getPopularMovies(language, page, region))
        emit(response)
    }.catch { e ->
        emit(ApiResponse.Error(e.localizedMessage ?: "Unexpected Error"))
    }.flowOn(ioDispatcher)

    override fun getTrendingMovies(
        timeWindow: String
    ) = flow {
        val response = apiResponseHandler(api.getTrendingMovies(timeWindow))
        emit(response)
    }.catch { e ->
        emit(ApiResponse.Error(e.localizedMessage ?: "Unexpected Error"))
    }.flowOn(ioDispatcher)

    override fun getPlayingNow(
        language: String?, page: Int?, region: String?
    ) = flow {
        val response = apiResponseHandler(api.getNowPlaying(language, page, region))
        emit(response)
    }.catch { e ->
        emit(ApiResponse.Error(e.localizedMessage ?: "Unexpected Error"))
    }.flowOn(ioDispatcher)

    override suspend fun searchMovies(
        query: String,
        language: String?,
        page: Int?,
        includeAdult: Boolean?,
        region: String?,
        year: Int?,
        primaryReleaseYear: Int?
    ): Response<SearchResponse> = withContext(ioDispatcher) {
        api.searchMovies(
            query, language, page, includeAdult, region, year, primaryReleaseYear
        )
    }

    override fun getMovieDetails(
        movieId: Int, language: String?, appendToResponse: String?
    ): Flow<ApiResponse<MovieDetailsResponse>> = flow {
        val response = api.getMovieDetails(movieId, language, appendToResponse)
        emit(apiResponseHandler(response))
    }.catch { e ->
        emit(ApiResponse.Error(e.localizedMessage ?: "Unexpected Error"))
    }.flowOn(ioDispatcher)

}

