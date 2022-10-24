package com.example.bookmark.di.presentation

import com.example.bookmark.domain.SaveMoviesUseCase
import com.example.bookmark.domain.SaveMoviesUseCaseImpl
import com.example.core.data.repository.MoviesRepository
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
    fun provide(moviesRepository: MoviesRepository): SaveMoviesUseCase =
        SaveMoviesUseCaseImpl(moviesRepository)

}