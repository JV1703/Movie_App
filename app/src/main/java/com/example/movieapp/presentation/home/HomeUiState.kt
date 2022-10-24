package com.example.movieapp.presentation.home

import com.example.core.domain.model.Movies

data class HomeUiState(
    val isLoading: Boolean = true,
    val nowPlayingMovies: List<Movies> = emptyList(),
    val popularMovies: List<Movies> = emptyList(),
    val trendingMovies: List<Movies> = emptyList(),
    val nowPlayingMsg: String? = null,
    val popularMoviesMsg: String? = null,
    val trendingMoviesMsg: String? = null
)