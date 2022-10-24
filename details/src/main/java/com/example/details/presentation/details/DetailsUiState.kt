package com.example.details.presentation.details

import com.example.core.domain.model.MovieDetails

data class MovieDetailsUiState(
    val isLoading: Boolean = true,
    val details: MovieDetails? = null,
    val userMessage: String? = null,
    val isBookmarked: Boolean = false,
    val initialLoad: Boolean = true,
)