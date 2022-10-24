package com.example.core.data.source.remote.response.movie_details


import com.squareup.moshi.Json

data class Genre(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)