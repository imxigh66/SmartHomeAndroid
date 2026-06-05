package com.example.smarthome.data.model

data class BillingPeriod(
    val id: String,
    val periodStart: String,
    val periodEnd: String,
    val totalConsumption: Double,
    val totalAmount: Double,
    val dayConsumption: Double,
    val nightConsumption: Double,
    val dayAmount: Double,
    val nightAmount: Double,
    val createdAt: String
)

data class CurrentMonthBilling(
    val monthConsumption: Double,
    val monthAmount: Double,
    val forecastTotal: Double,
    val dailyAverage: Double,
    val daysElapsed: Int,
    val daysRemaining: Int
)