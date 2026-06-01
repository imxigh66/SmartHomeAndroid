package com.example.smarthome.data.repository

import com.example.smarthome.data.api.RetrofitClient
import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.data.model.LoginResponse
import com.example.smarthome.data.model.RegisterRequest

class AuthRepository {
    private val api= RetrofitClient.api

    suspend fun login(email: String,password: String): LoginResponse{
        return api.login(LoginRequest(email,password))
    }

    suspend fun register(name:String,email: String,password: String): LoginResponse{
        return api.register(RegisterRequest(name,email,password))
    }
}