package com.example.smarthome.data.api

import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.data.model.LoginResponse
import com.example.smarthome.data.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService{
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/login")
    suspend fun register(@Body request: RegisterRequest): LoginResponse
}