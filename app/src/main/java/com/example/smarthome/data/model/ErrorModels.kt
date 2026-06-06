package com.example.smarthome.data.model

data class ValidationError(
    val field: String,
    val message: String
)

data class ValidationErrorResponse(
    val errors: List<ValidationError>
)