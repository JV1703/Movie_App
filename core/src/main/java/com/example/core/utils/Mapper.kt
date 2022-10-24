package com.example.core.utils

import com.example.core.data.source.local.entity.SavedMovieEntity
import com.example.core.data.source.remote.response.movie_details.MovieDetailsResponse
import com.example.core.data.source.remote.response.now_playing.NowPlayingResponse
import com.example.core.data.source.remote.response.popular_movies.PopularMoviesResponse
import com.example.core.data.source.remote.response.search.Result
import com.example.core.data.source.remote.response.trending_movies.TrendingMoviesResponse
import com.example.core.domain.model.*

object Mapper {

    fun toMovies(popularMoviesResponse: PopularMoviesResponse) = popularMoviesResponse.results.map {
        Movies(
            id = it.id,
            posterPath = it.posterPath,
            title = it.title,
            voteAverage = it.voteAverage,
            releaseDate = it.releaseDate
        )
    }

    fun toMovies(nowPlayingResponse: NowPlayingResponse) = nowPlayingResponse.results.map {
        Movies(
            id = it.id,
            posterPath = it.posterPath,
            title = it.title,
            voteAverage = it.voteAverage,
            releaseDate = it.releaseDate
        )
    }

    fun toMovies(trendingMoviesResponse: TrendingMoviesResponse) =
        trendingMoviesResponse.results.map {
            Movies(
                id = it.id,
                posterPath = it.posterPath,
                title = it.title,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate
            )
        }

    fun toSavedMovie(savedMovie: SavedMovieEntity) = savedMovie.run {
        SavedMovie(
            id = id,
            title = title,
            overview = overview,
            posterPath = posterPath,
            adult = adult,
            releaseDate = releaseDate
        )
    }

    fun toSavedMovieEntity(savedMovie: SavedMovie) = savedMovie.run {
        SavedMovieEntity(
            id = id,
            title = title,
            overview = overview,
            posterPath = posterPath,
            adult = adult,
            releaseDate = releaseDate
        )
    }

    fun toMovieDetails(response: MovieDetailsResponse) = response.run {
        MovieDetails(id = id,
            title = title,
            tagline = tagline,
            overview = overview,
            genres = genres,
            releaseDate = releaseDate,
            posterPath = posterPath,
            backdropPath = backdropPath,
            voteAverage = voteAverage,
            voteCount = voteCount,
            casts = credits?.cast ?: emptyList(),
            crews = credits?.crew?.sortedWith(compareBy({ it.job }, { it.name })) ?: emptyList(),
            videos = videos?.results?.filter { it.site == "YouTube" } ?: emptyList(),
            recommendations = recommendations?.results ?: emptyList(),
            runtime = runtime,
            adult = adult)
    }

    fun toSavedMovieEntities(response: MovieDetailsResponse) = response.run {
        SavedMovieEntity(
            id = id,
            title = title,
            posterPath = posterPath,
            overview = overview,
            adult = adult,
            releaseDate = releaseDate
        )
    }

    fun toSavedMovieEntity(movieDetails: MovieDetails) = movieDetails.run {
        SavedMovieEntity(
            id = id,
            title = title,
            posterPath = posterPath,
            overview = overview,
            adult = adult,
            releaseDate = releaseDate
        )
    }

    fun toSearchMovies(searchResult: Result): SearchMovies = SearchMovies(
        id = searchResult.id,
        title = searchResult.title,
        posterPath = searchResult.posterPath,
        overview = searchResult.overview,
        releaseDate = searchResult.releaseDate,
        adult = searchResult.adult
    )

    fun toCastList(movieDetails: MovieDetails) = CastList(
        casts = movieDetails.casts
    )

    fun toCrewList(movieDetails: MovieDetails) = CrewList(
        crews = movieDetails.crews
    )

}