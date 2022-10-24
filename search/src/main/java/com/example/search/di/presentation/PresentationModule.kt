package com.example.search.di.presentation

import com.example.core.data.repository.MoviesRepository
import com.example.search.domain.usecase.SearchMoviesUseCase
import com.example.search.domain.usecase.SearchMoviesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class PresentationModule {

    @Provides
    @ViewModelScoped
    fun searchMoviesUseCase(moviesRepository: MoviesRepository): SearchMoviesUseCase =
        SearchMoviesUseCaseImpl(moviesRepository)

}