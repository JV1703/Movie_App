package com.example.details.domain

import com.example.core.data.repository.MoviesRepository
import com.example.core.data.source.Resource
import com.example.core.domain.model.MovieDetails
import com.example.core.domain.model.SavedMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMovieDetailsUseCaseImpl(private val moviesRepository: MoviesRepository) :
    GetMovieDetailsUseCase {
    override fun invoke(
        movieId: Int, language: String?, appendToResponse: String?
    ): Flow<Resource<MovieDetails>> {
        return moviesRepository.getMovieDetails(movieId, language, appendToResponse)
    }

    override suspend fun saveMovie(movieDetails: MovieDetails) {
        moviesRepository.saveMovie(movieDetails)
    }

    override suspend fun saveMovie(savedMovie: SavedMovie) {
        moviesRepository.saveMovie(savedMovie)
    }

    override suspend fun deleteSavedMovie(id: Int) = moviesRepository.deleteSavedMovie(id)

    override fun getSavedMovies(): Flow<List<SavedMovie>> {
        return moviesRepository.getSavedMovies()
    }

    override fun getSavedMovieById(id: Int): Flow<SavedMovie?> {
        return moviesRepository.getSavedMovieById(id)
    }

    override fun isMovieSaved(id: Int): Flow<Boolean> {
        return moviesRepository.getSavedMovieById(id).map {
            it != null
        }
    }
}