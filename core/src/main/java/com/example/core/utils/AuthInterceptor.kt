package com.example.core.utils

import com.example.core.utils.Constants.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val apiKey = API_KEY
//        var token: String? = null
        val url = original.url.newBuilder().addQueryParameter("api_key", apiKey).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}