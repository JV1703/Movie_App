package com.example.core.data.source

import com.example.core.data.source.remote.network.ApiResponse

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Empty<T> : Resource<T>()
}

fun <T, A> resourceHandler(response: ApiResponse<T>, mapper: () -> A): Resource<A> {
    return when (response) {
        is ApiResponse.Success -> Resource.Success(mapper())
        is ApiResponse.Error -> Resource.Error(response.errorMessage)
        is ApiResponse.Empty -> Resource.Empty()
    }
}