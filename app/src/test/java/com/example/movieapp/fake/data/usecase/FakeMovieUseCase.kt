package com.example.movieapp.fake.data.usecase

import com.example.core.data.source.Resource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.apiResponseHandler
import com.example.core.data.source.resourceHandler
import com.example.core.domain.model.Movies
import com.example.movieapp.utils.DataDummy
import com.example.core.utils.Mapper
import com.example.movieapp.domain.MovieUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeMovieUseCase : MovieUseCase {

    private var isSuccess = true
    private var numberOfData = 0
    private var isBodyEmpty = false

    fun setIsSuccess(isSuccess: Boolean) {
        this@FakeMovieUseCase.isSuccess = isSuccess
        numberOfData = if (isSuccess) 10 else 0
    }

    fun setIsBodyEmpty(isEmpty: Boolean) {
        isBodyEmpty = isEmpty
    }

    override fun getNowPlaying(
        language: String?, page: Int?, region: String?
    ): Flow<Resource<List<Movies>>> {
        val model = DataDummy.generateNowPlayingResponse(numberOfData)
        val response =
            DataDummy.generateResponse(isSuccess, isBodyEmpty) { Response.success(model) }
        val apiResponse = apiResponseHandler(response)
        val resource = resourceHandler(apiResponse) {
            Mapper.toMovies((apiResponse as ApiResponse.Success).data)
        }
        return flow { emit(resource) }
    }

    override fun getPopularMovies(
        language: String?, page: Int?, region: String?
    ): Flow<Resource<List<Movies>>> {
        val model = DataDummy.generatePopularMoviesResponse(numberOfData)
        val response =
            DataDummy.generateResponse(isSuccess, isBodyEmpty) { Response.success(model) }
        val apiResponse = apiResponseHandler(response)
        val resource = resourceHandler(apiResponse) {
            Mapper.toMovies((apiResponse as ApiResponse.Success).data)
        }
        return flow { emit(resource) }
    }

    override fun getTrendingMovies(timeWindow: String): Flow<Resource<List<Movies>>> {
        val model = DataDummy.generateTrendingMoviesResponse(numberOfData)
        val response =
            DataDummy.generateResponse(isSuccess, isBodyEmpty) { Response.success(model) }
        val apiResponse = apiResponseHandler(response)
        val resource = resourceHandler(apiResponse) {
            Mapper.toMovies((apiResponse as ApiResponse.Success).data)
        }
        return flow { emit(resource) }
    }

}