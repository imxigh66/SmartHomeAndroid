package com.example.smarthome.data.model

data class LoginRequest(
    val email:String,
    val password:String
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val name: String,
    val email: String
)

data class RegisterRequest(
    val name: String,
    val email:String,
    val password:String,

)