package com.example.core.data.source.remote.response.popular_movies


import com.squareup.moshi.Json

data class PopularMoviesResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<Result>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)