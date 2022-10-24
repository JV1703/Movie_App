package com.example.core.data.source.remote.response.trending_movies


import com.squareup.moshi.Json

data class TrendingMoviesResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<Result>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)