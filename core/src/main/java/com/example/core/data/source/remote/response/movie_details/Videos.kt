package com.example.core.data.source.remote.response.movie_details


import com.squareup.moshi.Json

data class Videos(
    @Json(name = "results")
    val results: List<VideosResult>
)