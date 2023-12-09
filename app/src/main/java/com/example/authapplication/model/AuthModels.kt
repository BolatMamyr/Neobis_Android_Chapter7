package com.example.authapplication.model

data class TokenRequestBody(
    val password: String,
    val username: String
)

data class TokenResponse(val token: String)

data class CreateUserRequestBody(
    val email: String,
    val password: String,
    val username: String
)

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val errorResponse: ErrorResponse) : ApiResponse<Nothing>()
    data class Exception(val throwable: Throwable) : ApiResponse<Nothing>()
    data object Loading : ApiResponse<Nothing>()
}
data class CreateUserResponse(
    val id: Int,
    val username: String,
    val email: String
)

data class ErrorResponse(
    val status: Int,
    val message: String,
    val timestamp: String
)


