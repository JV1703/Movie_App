package com.example.core.utils

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Boolean>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}