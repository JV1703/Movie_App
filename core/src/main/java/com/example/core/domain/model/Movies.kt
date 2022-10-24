package com.example.core.domain.model

data class Movies(
    val id: Int,
    val posterPath: String?,
    val title: String?,
    val voteAverage: Double,
    val releaseDate: String?
)