package com.example.core.data.source.remote.response.movie_details


import com.squareup.moshi.Json

data class Credits(
    @Json(name = "cast")
    val cast: List<Cast>,
    @Json(name = "crew")
    val crew: List<Crew>
)