package com.example.smarthome.data.model

data class MeterReading(
    val id: String,
    val readingDate: String,
    val dayReading: Double,
    val nightReading: Double,
    val isTwoZone: Boolean,
    val inputMethod: String,
    val createdAt: String
)

data class AddReadingRequest(
    val readingDate: String,
    val dayReading: Double,
    val nightReading: Double,
    val isTwoZone: Boolean
)