package com.example.core.data.source.local

import com.example.core.data.source.local.entity.SavedMovieEntity
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {

    suspend fun saveMovie(movie: SavedMovieEntity)

    fun getSavedMovies(): Flow<List<SavedMovieEntity>>

    suspend fun deleteSavedMovie(id: Int)

    fun getSavedMovieById(id: Int): Flow<SavedMovieEntity?>
}