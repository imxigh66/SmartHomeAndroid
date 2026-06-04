package com.example.smarthome.data.api

import com.example.smarthome.data.model.DashboardResponse
import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.data.model.LoginResponse
import com.example.smarthome.data.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService{
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): LoginResponse

    @GET("dashboard")
    suspend fun getDashboard(@Query("userId") userId: String): DashboardResponse
}