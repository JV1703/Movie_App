package com.example.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.source.local.entity.SavedMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovie(movie: SavedMovieEntity)

    @Query("SELECT * FROM saved_movies_table")
    fun getSavedMovies(): Flow<List<SavedMovieEntity>>

    @Query("DELETE FROM saved_movies_table WHERE id = :id")
    suspend fun deleteSavedMovie(id: Int)

    @Query("SELECT * FROM saved_movies_table WHERE id = :id")
    fun getSavedMovieById(id: Int): Flow<SavedMovieEntity?>

}