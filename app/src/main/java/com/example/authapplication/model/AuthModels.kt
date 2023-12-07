package com.example.authapplication.model

data class TokenRequestBody(
    val password: String,
    val username: String
)

data class CreateUserRequestBody(
    val email: String,
    val password: String,
    val username: String
)

data class CreateUserResponse(
    val id: Int,
    val username: String,
    val email: String
)

data class CreateUserErrorResponse(
    val status: Int,
    val message: String,
    val timestamp: String
)


