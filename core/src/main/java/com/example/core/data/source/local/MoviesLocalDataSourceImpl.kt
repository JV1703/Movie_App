package com.example.core.data.source.local

import com.example.core.data.source.local.entity.SavedMovieEntity
import com.example.core.data.source.local.room.dao.MoviesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor(private val moviesDao: MoviesDao) :
    MoviesLocalDataSource {

    override suspend fun saveMovie(movie: SavedMovieEntity) {
        moviesDao.saveMovie(movie)
    }

    override fun getSavedMovies(): Flow<List<SavedMovieEntity>> = moviesDao.getSavedMovies()

    override suspend fun deleteSavedMovie(id: Int) = moviesDao.deleteSavedMovie(id)

    override fun getSavedMovieById(id: Int): Flow<SavedMovieEntity?> =
        moviesDao.getSavedMovieById(id)

}