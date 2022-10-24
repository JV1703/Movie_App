package com.example.core.fake.data.repository

import com.example.core.data.repository.MoviesRepository
import com.example.core.data.source.Resource
import com.example.core.data.source.local.entity.SavedMovieEntity
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.apiResponseHandler
import com.example.core.data.source.remote.response.search.SearchResponse
import com.example.core.data.source.resourceHandler
import com.example.core.domain.model.MovieDetails
import com.example.core.domain.model.Movies
import com.example.core.domain.model.SavedMovie
import com.example.core.utils.DataDummy
import com.example.core.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeMoviesRepository : MoviesRepository {

    private val fakeDatabase = arrayListOf<SavedMovieEntity>()

    private var isSuccess = true
    private var isBodyEmpty = false

    fun setIsSuccess(isSuccess: Boolean) {
        this@FakeMoviesRepository.isSuccess = isSuccess
    }

    fun setIsBodyEmpty(isEmpty: Boolean) {
        isBodyEmpty = isEmpty
    }

    override fun getPopularMovies(
        language: String?, page: Int?, region: String?
    ): Flow<Resource<List<Movies>>> {
        val response = DataDummy.generateResponse(
            isSuccess, isBodyEmpty
        ) { Response.success(DataDummy.generateNowPlayingResponse()) }
        val apiResponse = apiResponseHandler(response)
        return flow {
            emit(resourceHandler(apiResponse) {
                Mapper.toMovies((apiResponse as ApiResponse.Success).data)
            })
        }
    }

    override fun getTrendingMovies(timeWindow: String): Flow<Resource<List<Movies>>> {
        val response = DataDummy.generateResponse(
            isSuccess, isBodyEmpty
        ) { Response.success(DataDummy.generateTrendingMoviesResponse()) }
        val apiResponse = apiResponseHandler(response)
        return flow {
            emit(resourceHandler(apiResponse) {
                Mapper.toMovies((apiResponse as ApiResponse.Success).data)
            })
        }
    }

    override fun getNowPlaying(
        language: String?, page: Int?, region: String?
    ): Flow<Resource<List<Movies>>> {
        val response = DataDummy.generateResponse(
            isSuccess, isBodyEmpty
        ) { Response.success(DataDummy.generateNowPlayingResponse()) }
        val apiResponse = apiResponseHandler(response)
        return flow {
            emit(resourceHandler(apiResponse) {
                Mapper.toMovies((apiResponse as ApiResponse.Success).data)
            })
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
    ): Resource<SearchResponse> {
        val model = DataDummy.generateSearchMovieResponse()
        val response =
            DataDummy.generateResponse(isSuccess, isBodyEmpty) { Response.success(model) }
        val apiResponse = apiResponseHandler(response)
        return resourceHandler(apiResponse) {
            (apiResponse as ApiResponse.Success).data
        }
    }

    override fun getMovieDetails(
        movieId: Int, language: String?, appendToResponse: String?
    ): Flow<Resource<MovieDetails>> {
        val model = DataDummy.generateMovieDetailsResponse()
        val response =
            DataDummy.generateResponse(isSuccess, isBodyEmpty) { Response.success(model) }
        val apiResponse = apiResponseHandler(response)
        val resource = resourceHandler(apiResponse) {
            Mapper.toMovieDetails((apiResponse as ApiResponse.Success).data)
        }
        return flow {
            emit(resource)
        }
    }

    override suspend fun saveMovie(movieDetails: MovieDetails) {
        fakeDatabase.add(Mapper.toSavedMovieEntity(movieDetails))
    }

    override suspend fun saveMovie(savedMovie: SavedMovie) {
        fakeDatabase.add(Mapper.toSavedMovieEntity(savedMovie))
    }

    override fun getSavedMovies(): Flow<List<SavedMovie>> {
        return flow {
            emit(fakeDatabase.map {
                Mapper.toSavedMovie(it)
            })
        }
    }

    override suspend fun deleteSavedMovie(id: Int) {
        fakeDatabase.removeAt(fakeDatabase.indexOfFirst { it.id == id })
    }

    override fun getSavedMovieById(id: Int): Flow<SavedMovie?> {
        val data = fakeDatabase.filter {
            it.id == id
        }
        return flow {
            emit(
                if (data.isEmpty()) {
                    null
                } else {
                    Mapper.toSavedMovie(data[0])
                }
            )
        }
    }

}