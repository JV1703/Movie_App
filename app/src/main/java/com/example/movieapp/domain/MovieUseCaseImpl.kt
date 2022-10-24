package com.example.movieapp.domain

import com.example.core.data.repository.MoviesRepository
import com.example.core.data.source.Resource
import com.example.core.domain.model.Movies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(private val moviesRepository: MoviesRepository) :
    MovieUseCase {

    override fun getNowPlaying(
        language: String?, page: Int?, region: String?
    ): Flow<Resource<List<Movies>>> {
        return moviesRepository.getNowPlaying(language, page, region)
    }

    override fun getPopularMovies(
        language: String?, page: Int?, region: String?
    ): Flow<Resource<List<Movies>>> {
        return moviesRepository.getPopularMovies(language, page, region)
    }

    override fun getTrendingMovies(timeWindow: String): Flow<Resource<List<Movies>>> {
        return moviesRepository.getTrendingMovies(timeWindow)
    }

}