package com.example.core.di

import com.example.core.data.source.remote.network.ApiService
import com.example.core.utils.AuthInterceptor
import com.example.core.utils.Constants.BASE_URL
import com.example.core.utils.Constants.TMDB_HOST_NAME
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun apiKeyInterceptor() = AuthInterceptor()

    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun certificatePinner() = CertificatePinner.Builder()
        .add(TMDB_HOST_NAME, "sha256/p+WeEuGncQbjSKYPSzAaKpF/iLcOjFLuZubtsXupYSI=")
        .add(TMDB_HOST_NAME, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
        .add(TMDB_HOST_NAME, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
        .add(TMDB_HOST_NAME, "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
        .build()

    @Provides
    @Singleton
    fun okHttpClient(
        apiKeyInterceptor: AuthInterceptor, loggingInterceptor: HttpLoggingInterceptor, certificatePinner: CertificatePinner
    ) = OkHttpClient.Builder().addInterceptor(apiKeyInterceptor).addInterceptor(loggingInterceptor).certificatePinner(certificatePinner)
        .readTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun moshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun moshiConverterFactory(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun retrofit(
        moshiConverterFactory: MoshiConverterFactory, okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().addConverterFactory(moshiConverterFactory).client(okHttpClient)
        .baseUrl(BASE_URL).build()

    @Provides
    @Singleton
    fun movieApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}