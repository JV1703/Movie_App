package com.example.movieapp.presentation.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.source.Resource
import com.example.core.domain.model.Movies
import com.example.movieapp.domain.MovieUseCase
import com.example.moviesapp.presentation.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase/*,
    private val connectivityObserver: ConnectivityObserver*/
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
//        observeNetwork()
        getMovies(page = 1, timeWindow = "day")
    }

//    private fun observeNetwork() {
//        connectivityObserver.observe().onEach {
//            Timber.tag("HomeViewModel").i("network status: %s", it)
//        }.launchIn(viewModelScope)
//    }

    fun getMovies(
        language: String? = null, page: Int? = null, region: String? = null, timeWindow: String
    ) {
        combine(
            movieUseCase.getNowPlaying(language, page, region),
            movieUseCase.getPopularMovies(language, page, region),
            movieUseCase.getTrendingMovies(timeWindow)
        ) { nowPlaying, popularMovies, trendingMovies ->
            updateUiState(nowPlaying, popularMovies, trendingMovies)
        }.launchIn(viewModelScope)
    }

    private fun updateUiState(
        nowPlaying: Resource<List<Movies>>,
        popular: Resource<List<Movies>>,
        trending: Resource<List<Movies>>
    ) {
        _homeUiState.value = HomeUiState(
            isLoading = false,
            nowPlayingMovies = nowPlaying.data ?: emptyList(),
            popularMovies = popular.data ?: emptyList(),
            trendingMovies = trending.data ?: emptyList(),
            nowPlayingMsg = nowPlaying.message,
            popularMoviesMsg = popular.message,
            trendingMoviesMsg = trending.message
        )
    }


}
