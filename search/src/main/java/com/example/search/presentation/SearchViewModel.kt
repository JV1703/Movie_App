package com.example.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.search.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SearchViewModel(private val searchMoviesUseCase: SearchMoviesUseCase) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow().debounce(500).distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }.flatMapLatest {
            searchMoviesUseCase(it).cachedIn(viewModelScope)
        }

    fun updateSearchQuery(searchQuery: String) {
        _searchQuery.value = searchQuery
    }

}

class SearchViewModelFactory @Inject constructor(private val searchMoviesUseCase: SearchMoviesUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(searchMoviesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}