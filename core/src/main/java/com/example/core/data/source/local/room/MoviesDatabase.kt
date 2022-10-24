package com.example.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.source.local.entity.SavedMovieEntity
import com.example.core.data.source.local.room.dao.MoviesDao

@Database(entities = [SavedMovieEntity::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}