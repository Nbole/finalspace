package com.example.finalspace.data

sealed class NResponse<T> {
    data class Success<T>(val data: T) : NResponse<T>()
    data class Loading<T>(val data: T? = null) : NResponse<T>()
    data class Error<T>(val message: String, val data: T? = null) : NResponse<T>()
}
