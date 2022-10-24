package com.example.bookmark.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bookmark.domain.SaveMoviesUseCase
import com.example.core.domain.model.SavedMovie
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookmarkViewModel(private val useCase: SaveMoviesUseCase) : ViewModel() {

    val savedMovies = useCase.getSavedMovies()

    fun deleteMovie(apiId: Int) {
        viewModelScope.launch {
            useCase.deleteSavedMovie(apiId)
        }
    }

    fun saveMovie(savedMovie: SavedMovie) {
        viewModelScope.launch {
            useCase.saveMovie(savedMovie)
        }
    }

}

class BookmarkViewModelFactory @Inject constructor(private val useCase: SaveMoviesUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}