package com.example.details.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.MovieDetails
import com.example.details.domain.GetMovieDetailsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModel(
    private val useCase: GetMovieDetailsUseCase/*,
    private val networkConnectivityObserver: ConnectivityObserver*/
) : ViewModel() {

    private var _movieId = MutableStateFlow(0)
    private val movieId = _movieId.asStateFlow()

    private val resource = movieId.flatMapLatest { useCase(it) }

    private val isBookmarked = movieId.flatMapLatest { useCase.isMovieSaved(it) }


    fun saveMovieId(movieId: Int) {
        _movieId.value = movieId
    }

    val uiState: StateFlow<MovieDetailsUiState> =
        combine(isBookmarked, resource) { isBookmarked, resource ->
            MovieDetailsUiState(
                isLoading = false,
                details = resource.data,
                userMessage = resource.message,
                isBookmarked = isBookmarked,
                initialLoad = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = MovieDetailsUiState()
        )

    fun saveMovie(movie: MovieDetails?) {
        viewModelScope.launch {
            if (movie != null) {
                useCase.saveMovie(movie)
            }
        }
    }

    fun deleteSavedMovie(movie: MovieDetails?) {
        viewModelScope.launch {
            if (movie != null) {
                useCase.deleteSavedMovie(movie.id)
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory @Inject constructor(
    private val useCase: GetMovieDetailsUseCase/*,
    private val networkConnectivityObserver: ConnectivityObserver*/
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(
                DetailsViewModel(
                    useCase/*, networkConnectivityObserver*/
                )::class.java
            )
        ) {
            return DetailsViewModel(useCase/*, networkConnectivityObserver*/) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}