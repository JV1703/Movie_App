package com.example.movieapp.fake.data.source

import com.example.core.data.source.local.MoviesLocalDataSource
import com.example.core.data.source.local.entity.SavedMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMoviesLocalDataSource : MoviesLocalDataSource {

    private val fakeDatabase = arrayListOf<SavedMovieEntity>()

    override suspend fun saveMovie(movie: SavedMovieEntity) {
        fakeDatabase.add(movie)
    }

    override fun getSavedMovies(): Flow<List<SavedMovieEntity>> {
        return flow { emit(fakeDatabase) }
    }

    override suspend fun deleteSavedMovie(id: Int) {
        fakeDatabase.removeAt(fakeDatabase.indexOfFirst { it.id == id })
    }

    override fun getSavedMovieById(id: Int): Flow<SavedMovieEntity?> {
        val data = fakeDatabase.filter { it.id == id }
        return flow { emit(data[0]) }
    }
}