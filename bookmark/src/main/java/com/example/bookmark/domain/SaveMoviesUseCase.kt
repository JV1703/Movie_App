package com.example.bookmark.domain

import com.example.core.domain.model.MovieDetails
import com.example.core.domain.model.SavedMovie
import kotlinx.coroutines.flow.Flow

interface SaveMoviesUseCase {

    suspend fun saveMovie(movieDetails: MovieDetails)

    fun getSavedMovies(): Flow<List<SavedMovie>>

    suspend fun deleteSavedMovie(apiId: Int)

    suspend fun saveMovie(savedMovie: SavedMovie)

}