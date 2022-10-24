package com.example.core.di.dependency

import com.example.core.data.repository.MoviesRepository
import com.example.core.utils.ConnectivityObserver
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SharedDependency {

    fun provideMoviesRepository(): MoviesRepository
    fun provideNetworkConnectivity(): ConnectivityObserver

}