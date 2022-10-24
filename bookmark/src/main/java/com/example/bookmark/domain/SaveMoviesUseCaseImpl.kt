package com.example.bookmark.domain

import com.example.core.data.repository.MoviesRepository
import com.example.core.domain.model.MovieDetails
import com.example.core.domain.model.SavedMovie
import kotlinx.coroutines.flow.Flow

class SaveMoviesUseCaseImpl(private val repository: MoviesRepository) : SaveMoviesUseCase {

    override suspend fun saveMovie(movieDetails: MovieDetails) {
        repository.saveMovie(movieDetails)
    }

    override suspend fun saveMovie(savedMovie: SavedMovie) {
        repository.saveMovie(savedMovie)
    }

    override fun getSavedMovies(): Flow<List<SavedMovie>> {
        return repository.getSavedMovies()
    }

    override suspend fun deleteSavedMovie(apiId: Int) {
        repository.deleteSavedMovie(apiId)
    }

}