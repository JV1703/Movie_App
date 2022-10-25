package com.example.movieapp.fake.data.usecase

import com.example.core.data.source.Resource
import com.example.core.data.source.local.entity.SavedMovieEntity
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.apiResponseHandler
import com.example.core.data.source.resourceHandler
import com.example.core.domain.model.MovieDetails
import com.example.core.domain.model.SavedMovie
import com.example.movieapp.utils.DataDummy
import com.example.core.utils.Mapper
import com.example.details.domain.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeMovieDetailsUseCase : GetMovieDetailsUseCase {

    private val fakeDatabase = arrayListOf<SavedMovieEntity>()

    private var isSuccess = true
    private var numberOfData = 0
    private var isBodyEmpty = false

    fun setIsSuccess(isSuccess: Boolean) {
        this@FakeMovieDetailsUseCase.isSuccess = isSuccess
        numberOfData = if (isSuccess) 10 else 0
    }

    override fun invoke(
        movieId: Int, language: String?, appendToResponse: String?
    ): Flow<Resource<MovieDetails>> {
        val model = MovieDetails(
            id = movieId,
            title = "",
            tagline = "",
            overview = "",
            genres = emptyList(),
            releaseDate = "",
            posterPath = "",
            backdropPath = "",
            voteAverage = 0.0,
            voteCount = 0,
            casts = emptyList(),
            crews = emptyList(),
            videos = emptyList(),
            recommendations = emptyList(),
            runtime = 0,
            adult = true
        )
        val response =
            DataDummy.generateResponse(isSuccess, isBodyEmpty) { Response.success(model) }
        val apiResponse = apiResponseHandler(response)
        val resource = resourceHandler(apiResponse) { (apiResponse as ApiResponse.Success).data }
        return flow { emit(resource) }
    }

    override suspend fun saveMovie(movieDetails: MovieDetails) {
        fakeDatabase.add(Mapper.toSavedMovieEntity(movieDetails))
    }

    override suspend fun saveMovie(savedMovie: SavedMovie) {
        fakeDatabase.add(Mapper.toSavedMovieEntity(savedMovie))
    }

    override suspend fun deleteSavedMovie(apiId: Int) {
        fakeDatabase.removeAt(fakeDatabase.indexOfFirst { it.id == apiId })
    }

    override fun getSavedMovies(): Flow<List<SavedMovie>> {
        return flow {
            emit(fakeDatabase.map {
                Mapper.toSavedMovie(it)
            })
        }
    }

    override fun getSavedMovieById(id: Int): Flow<SavedMovie?> {
        val data = fakeDatabase.filter { it.id == id }
        return flow {
            emit(if (data.isEmpty()) null else Mapper.toSavedMovie(data[0]))
        }
    }

    override fun isMovieSaved(id: Int): Flow<Boolean> {
        val data = fakeDatabase.filter { it.id == id }
        return flow { emit(data.isNotEmpty()) }
    }

}