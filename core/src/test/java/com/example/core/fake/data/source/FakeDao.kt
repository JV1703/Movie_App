package com.example.core.fake.data.source

import com.example.core.data.source.local.entity.SavedMovieEntity
import com.example.core.data.source.local.room.dao.MoviesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDao : MoviesDao {

    private val fakeDatabase = arrayListOf<SavedMovieEntity>()

    override suspend fun saveMovie(movie: SavedMovieEntity) {
        fakeDatabase.add(movie)
    }

    override fun getSavedMovies(): Flow<List<SavedMovieEntity>> {
        return flow { emit(fakeDatabase) }
    }

    override suspend fun deleteSavedMovie(apiId: Int) {
        fakeDatabase.removeAt(fakeDatabase.indexOfFirst { it.id == apiId })
    }

    override fun getSavedMovieById(apiId: Int): Flow<SavedMovieEntity?> {
        val data = fakeDatabase.filter { it.id == apiId }
        return flow {
            emit(
                if (data.isEmpty()) null else data[0]
            )
        }
    }

}