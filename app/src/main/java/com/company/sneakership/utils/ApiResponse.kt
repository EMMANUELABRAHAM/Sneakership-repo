package com.company.sneakership.utils

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T, val statusCode: Int? = null) : ApiResponse<T>()
    data class Error(val errorMessage: String, val statusCode: Int? = null) : ApiResponse<Nothing>()
}
