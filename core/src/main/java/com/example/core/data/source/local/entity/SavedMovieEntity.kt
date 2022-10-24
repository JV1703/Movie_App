package com.example.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_movies_table")
data class SavedMovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val adult: Boolean,
    val releaseDate: String?
)