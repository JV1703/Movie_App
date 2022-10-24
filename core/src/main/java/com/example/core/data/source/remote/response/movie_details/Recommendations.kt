package com.example.core.data.source.remote.response.movie_details


import com.squareup.moshi.Json

data class Recommendations(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<RecommendationResult>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)