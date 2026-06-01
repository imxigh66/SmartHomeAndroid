package com.example.smarthome.data.model

data class LoginRequest(
    val email:String,
    val password:String
)

data class LoginResponse(
    val token:String,
    val userId: String
)

data class RegisterRequest(
    val name: String,
    val email:String,
    val password:String,

)