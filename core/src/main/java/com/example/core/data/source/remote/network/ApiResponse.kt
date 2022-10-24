package com.example.core.data.source.remote.network

import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}

fun <T> apiResponseHandler(response: Response<T>): ApiResponse<T> {

    return when {
        response.message().toString().contains("timeout") -> {
            Timber.e("networkResultHandler - timeout")
            ApiResponse.Error("Timeout")
        }
        response.errorBody() != null -> {
            val jObjError = JSONObject(response.errorBody()?.string() ?: "")
            val errorBody = jObjError.getString("status_message")
            Timber.e("network_result", errorBody)
            ApiResponse.Error(
                "${response.code()}, error: $errorBody"
            )
        }
        response.isSuccessful -> {
            if (response.body() == null) {
                Timber.i("networkResultHandler - isSuccessful body empty")
                ApiResponse.Empty
            } else {
                val body = response.body()
                Timber.i("networkResultHandler - isSuccessful body not empty")
                ApiResponse.Success(body!!)
            }
        }
        else -> {
            Timber.e("networkResultHandler - else - error code: ${response.code()}, msg: ${response.message()}")
            ApiResponse.Error("error code: ${response.code()}, msg: $response")
        }
    }
}
