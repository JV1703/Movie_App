package com.example.details.di.presentation

import com.example.core.data.repository.MoviesRepository
import com.example.details.domain.GetMovieDetailsUseCase
import com.example.details.domain.GetMovieDetailsUseCaseImpl
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
    fun provide(moviesRepository: MoviesRepository): GetMovieDetailsUseCase =
        GetMovieDetailsUseCaseImpl(moviesRepository)

}