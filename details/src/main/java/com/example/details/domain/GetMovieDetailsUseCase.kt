package com.example.details.domain

import com.example.core.data.source.Resource
import com.example.core.domain.model.MovieDetails
import com.example.core.domain.model.SavedMovie
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsUseCase {

    operator fun invoke(
        movieId: Int,
        language: String? = null,
        appendToResponse: String? = "videos,recommendations,credits"
    ): Flow<Resource<MovieDetails>>

    suspend fun saveMovie(movieDetails: MovieDetails)

    suspend fun deleteSavedMovie(id: Int)

    fun getSavedMovies(): Flow<List<SavedMovie>>

    suspend fun saveMovie(savedMovie: SavedMovie)

    fun getSavedMovieById(id: Int): Flow<SavedMovie?>

    fun isMovieSaved(id: Int): Flow<Boolean>
}