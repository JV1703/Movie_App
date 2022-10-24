package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.repository.MoviesRepository
import com.example.core.data.repository.MoviesRepositoryImpl
import com.example.core.data.source.local.MoviesLocalDataSource
import com.example.core.data.source.local.MoviesLocalDataSourceImpl
import com.example.core.data.source.local.room.MoviesDatabase
import com.example.core.data.source.local.room.dao.MoviesDao
import com.example.core.data.source.remote.MoviesRemoteDataSource
import com.example.core.data.source.remote.MoviesRemoteDataSourceImpl
import com.example.core.data.source.remote.network.ApiService
import com.example.core.utils.ConnectivityObserver
import com.example.core.utils.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext application: Context): ConnectivityObserver =
        NetworkConnectivityObserver(application)

    val passphrase: ByteArray = SQLiteDatabase.getBytes("Banana".toCharArray())
    val factory = SupportFactory(passphrase)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): MoviesDatabase =
        Room.databaseBuilder(context, MoviesDatabase::class.java, "movies_database")
            .openHelperFactory(factory).build()

    @Singleton
    @Provides
    fun provideMoviesDao(moviesDatabase: MoviesDatabase) = moviesDatabase.moviesDao()

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        api: ApiService, @CoroutinesQualifiers.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MoviesRemoteDataSource = MoviesRemoteDataSourceImpl(api, ioDispatcher)

    @Provides
    @Singleton
    fun provideLocalDataSource(
        moviesDao: MoviesDao
    ): MoviesLocalDataSource = MoviesLocalDataSourceImpl(moviesDao)

    @Provides
    @Singleton
    fun provideMoviesRepository(
        remote: MoviesRemoteDataSource,
        local: MoviesLocalDataSource,
        @CoroutinesQualifiers.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MoviesRepository = MoviesRepositoryImpl(remote, local, ioDispatcher)

}