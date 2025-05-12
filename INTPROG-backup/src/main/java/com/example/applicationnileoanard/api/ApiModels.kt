package com.example.applicationnileoanard.api

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class ApiResponse(
    val message: String,
    val user: UserData? = null
)

data class UserData(
    val username: String,
    val email: String
) 