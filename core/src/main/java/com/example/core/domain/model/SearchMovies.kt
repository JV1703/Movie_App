package com.example.core.domain.model

data class SearchMovies(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val releaseDate: String?,
    val adult: Boolean,
    /*val page: Int?,
    val totalPage:Int?*/
)