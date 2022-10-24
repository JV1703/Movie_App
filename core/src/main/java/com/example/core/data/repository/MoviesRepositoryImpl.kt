package com.example.core.data.repository

import com.example.core.data.source.Resource
import com.example.core.data.source.local.MoviesLocalDataSource
import com.example.core.data.source.remote.MoviesRemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.apiResponseHandler
import com.example.core.data.source.remote.response.search.SearchResponse
import com.example.core.data.source.resourceHandler
import com.example.core.di.CoroutinesQualifiers
import com.example.core.domain.model.MovieDetails
import com.example.core.domain.model.SavedMovie
import com.example.core.utils.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remote: MoviesRemoteDataSource,
    private val local: MoviesLocalDataSource,
    @CoroutinesQualifiers.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MoviesRepository {

    override fun getPopularMovies(language: String?, page: Int?, region: String?) =
        remote.getPopularMovies(language, page, region).map { apiResponse ->
            resourceHandler(apiResponse) {
                Mapper.toMovies((apiResponse as ApiResponse.Success).data)
            }
        }.flowOn(ioDispatcher)

    override fun getTrendingMovies(timeWindow: String) =
        remote.getTrendingMovies(timeWindow).map { apiResponse ->
            resourceHandler(apiResponse) {
                Mapper.toMovies((apiResponse as ApiResponse.Success).data)
            }
        }.flowOn(ioDispatcher)

    override fun getNowPlaying(language: String?, page: Int?, region: String?) =
        remote.getPlayingNow(language, page, region).map { apiResponse ->
            resourceHandler(apiResponse) {
                Mapper.toMovies((apiResponse as ApiResponse.Success).data)
            }
        }.flowOn(ioDispatcher)

    override suspend fun searchMovies(
        query: String,
        language: String?,
        page: Int?,
        includeAdult: Boolean?,
        region: String?,
        year: Int?,
        primaryReleaseYear: Int?
    ): Resource<SearchResponse> {
        val response = apiResponseHandler(
            remote.searchMovies(
                query, language, page, includeAdult, region, year, primaryReleaseYear
            )
        )
        return resourceHandler(response) {
            (response as ApiResponse.Success).data
        }
    }

    override fun getMovieDetails(
        movieId: Int, language: String?, appendToResponse: String?
    ) = remote.getMovieDetails(movieId, language, appendToResponse).map { apiResponse ->
        resourceHandler(apiResponse) {
            Mapper.toMovieDetails((apiResponse as ApiResponse.Success).data)
        }
    }

    override suspend fun saveMovie(movieDetails: MovieDetails) {
        local.saveMovie(Mapper.toSavedMovieEntity(movieDetails))
    }

    override suspend fun saveMovie(savedMovie: SavedMovie) {
        local.saveMovie(Mapper.toSavedMovieEntity(savedMovie))
    }

    override fun getSavedMovies(): Flow<List<SavedMovie>> = local.getSavedMovies().map {
        it.map { savedMovieEntity ->
            Mapper.toSavedMovie(savedMovieEntity) }
    }.flowOn(ioDispatcher)

    override suspend fun deleteSavedMovie(id: Int) = local.deleteSavedMovie(id)

    override fun getSavedMovieById(id: Int): Flow<SavedMovie?> =
        local.getSavedMovieById(id).map {
            it?.let { Mapper.toSavedMovie(it) }
        }

}