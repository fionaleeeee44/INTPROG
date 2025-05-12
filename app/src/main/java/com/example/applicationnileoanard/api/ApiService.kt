package com.example.applicationnileoanard.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse>
}