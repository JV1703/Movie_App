package com.example.core.domain.model

import android.os.Parcelable
import com.example.core.data.source.remote.response.movie_details.*
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class MovieDetails(
    val id: Int,
    val title: String,
    val tagline: String,
    val overview: String,
    val genres: List<Genre>,
    val releaseDate: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double?,
    val voteCount: Int,
    val casts: List<Cast>,
    val crews: List<Crew>,
    val videos: List<VideosResult>,
    val recommendations: List<RecommendationResult>,
    val runtime: Int,
    val adult: Boolean
)

@Parcelize
data class CastList(
    val casts:@RawValue List<Cast>
) : Parcelable

@Parcelize
data class CrewList(
    val crews:@RawValue List<Crew>
) : Parcelable