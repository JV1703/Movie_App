package com.example.movieapp.utils

import com.example.core.data.source.local.entity.SavedMovieEntity
import com.example.core.data.source.remote.response.movie_details.MovieDetailsResponse
import com.example.core.data.source.remote.response.now_playing.Dates
import com.example.core.data.source.remote.response.now_playing.NowPlayingResponse
import com.example.core.data.source.remote.response.popular_movies.PopularMoviesResponse
import com.example.core.data.source.remote.response.search.SearchResponse
import com.example.core.data.source.remote.response.trending_movies.TrendingMoviesResponse
import com.example.core.domain.model.MovieDetails
import com.example.core.domain.model.SavedMovie
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import com.example.core.data.source.remote.response.now_playing.Result as nowPlayingResult
import com.example.core.data.source.remote.response.popular_movies.Result as popularMoviesResult
import com.example.core.data.source.remote.response.search.Result as searchResult
import com.example.core.data.source.remote.response.trending_movies.Result as trendingMoviesResult

object DataDummy {

    fun <T> generateResponse(
        isSuccess: Boolean = true, isBodyEmpty: Boolean = false, successResponse: () -> Response<T>
    ): Response<T> {
        return if (!isSuccess) {
            Response.error(
                404,
                "{\"status_message\":\"Not Found\"}".toResponseBody("application/json".toMediaTypeOrNull())
            )
        } else {
            if (!isBodyEmpty) {
                successResponse()
            } else {
                Response.success(null)
            }
        }
    }

    fun generatePopularMoviesResponse(numberOfResult: Int = 10) = PopularMoviesResponse(
        page = 1,
        results = generatePopularMoviesResult(numberOfResult),
        totalPages = 10,
        totalResults = 100
    )

    private fun generatePopularMoviesResult(n: Int = 10): List<popularMoviesResult> {
        val listOfPopularMoviesResult = arrayListOf<popularMoviesResult>()
        for (i in 1..n) {
            val result = popularMoviesResult(
                true, "", listOf(), i, "", "", "", 0.0, "", "", "", true, 0.0, 0
            )
            listOfPopularMoviesResult.add(result)
        }
        return listOfPopularMoviesResult
    }

    fun generateTrendingMoviesResponse(numberOfResult: Int = 10) = TrendingMoviesResponse(
        page = 1,
        results = generateTrendingMoviesResult(numberOfResult),
        totalPages = 10,
        totalResults = 100
    )

    private fun generateTrendingMoviesResult(n: Int = 10): List<trendingMoviesResult> {
        val listOfTrendingMoviesResult = arrayListOf<trendingMoviesResult>()
        for (i in 1..n) {
            val result = trendingMoviesResult(
                true,
                "",
                "",
                listOf(),
                i,
                "",
                "",
                listOf(),
                "",
                "",
                "",
                "",
                0.0,
                "",
                "",
                "",
                true,
                0.0,
                0
            )
            listOfTrendingMoviesResult.add(result)
        }
        return listOfTrendingMoviesResult
    }

    fun generateNowPlayingResponse(numberOfResult: Int = 10): NowPlayingResponse {
        val dates = Dates("2022-10-08", "2022-10-08")
        return NowPlayingResponse(
            page = 1,
            results = generateNowPlayingResult(numberOfResult),
            totalPages = 10,
            dates = dates,
            totalResults = 100
        )
    }

    private fun generateNowPlayingResult(n: Int = 10): List<nowPlayingResult> {
        val listOfPopularMoviesResult = arrayListOf<nowPlayingResult>()
        for (i in 1..n) {
            val result = nowPlayingResult(
                true,
                "",
                listOf(),
                i,
                "", "",
                "",
                0.0,
                "",
                "",
                "",
                true,
                0.0,
                0,
            )
            listOfPopularMoviesResult.add(result)
        }
        return listOfPopularMoviesResult
    }

    fun generateSearchMovieResponse(numberOfResult: Int = 10): SearchResponse {
        return SearchResponse(
            page = 1,
            results = generateSearchResult(numberOfResult),
            totalPages = 10,
            totalResults = 100
        )
    }

    fun generateSearchResult(n: Int = 10): List<searchResult> {
        val listOfSearchResult = arrayListOf<searchResult>()
        for (i in 1..n) {
            val result = searchResult(
                adult = true,
                backdropPath = "",
                genreIds = listOf(1),
                id = i,
                originalLanguage = "",
                originalTitle = "",
                overview = "",
                posterPath = "",
                popularity = 0.0,
                releaseDate = "2022-10-08",
                title = "",
                video = true,
                voteAverage = 0.0,
                voteCount = 0
            )
            listOfSearchResult.add(result)
        }
        return listOfSearchResult
    }

    fun generateSavedMovieEntity(n: Int): ArrayList<SavedMovieEntity> {
        val listOfSavedMovieEntity = arrayListOf<SavedMovieEntity>()
        for (i in 1..n) {
            val data = SavedMovieEntity(
                id = i,
                title = "",
                posterPath = "",
                overview = "",
                adult = true,
                releaseDate = ""
            )
            listOfSavedMovieEntity.add(data)
        }
        return listOfSavedMovieEntity
    }

    fun generateMovieDetailsResponse(): MovieDetailsResponse {
        return MovieDetailsResponse(
            adult = true,
            backdropPath = "",
            belongsToCollection = null,
            budget = 0,
            credits = null,
            genres = emptyList(),
            homepage = "",
            id = 0,
            imdbId = "",
            originalLanguage = "",
            originalTitle = "",
            overview = "",
            popularity = 0.0,
            posterPath = "",
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            recommendations = null,
            releaseDate = "",
            revenue = 0,
            runtime = 0,
            spokenLanguages = emptyList(),
            status = "",
            tagline = "",
            title = "",
            video = true,
            videos = null,
            voteAverage = 0.0,
            voteCount = 0
        )
    }

    fun generateSavedMovie() = SavedMovie(
        id = 0,
        title = "",
        posterPath = "",
        overview = "",
        adult = true,
        releaseDate = ""
    )

    fun generateMovieDetails(id: Int = 0) = MovieDetails(
        id = id,
        title = "",
        tagline = "",
        overview = "",
        genres = emptyList(),
        releaseDate = "",
        posterPath = "",
        backdropPath = "",
        voteAverage = 0.0,
        voteCount = 0,
        casts = emptyList(),
        crews = emptyList(),
        videos = emptyList(),
        recommendations = emptyList(),
        runtime = 0,
        adult = true
    )

}