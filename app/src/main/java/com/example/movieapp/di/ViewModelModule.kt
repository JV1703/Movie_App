package com.example.movieapp.di

import com.example.core.data.repository.MoviesRepository
import com.example.movieapp.domain.MovieUseCase
import com.example.movieapp.domain.MovieUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideMovieUseCase(repository: MoviesRepository): MovieUseCase =
        MovieUseCaseImpl(repository)

}