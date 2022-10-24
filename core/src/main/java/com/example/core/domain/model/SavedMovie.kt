package com.example.core.domain.model

data class SavedMovie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val adult: Boolean,
    val releaseDate: String?
)