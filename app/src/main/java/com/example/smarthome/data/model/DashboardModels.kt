package com.example.smarthome.data.model


data class DashboardResponse(
    val forecastTotal: Double?,
    val monthAmount: Double?,
    val monthConsumption: Double?,
    val daysElapsed: Int,
    val daysRemaining: Int,
    val lastReadingDate: String?,
    val lastReadingValue: Double?,
    val provider: String,
    val planType: String,
    val currentRate: Double,
    val recommendation: String?,
    val hasReadings: Boolean
)