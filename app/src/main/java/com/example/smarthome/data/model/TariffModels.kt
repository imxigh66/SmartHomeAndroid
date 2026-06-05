package com.example.smarthome.data.model

data class MyTariff(
    val id: String,
    val provider: String,
    val planType: String,
    val singleRate: Double,
    val dayRate: Double,
    val nightRate: Double,
    val isManualOverride: Boolean
)

data class GlobalTariff(
    val provider: String,
    val planType: String,
    val rate: Double,
    val zone: String,
    val validFrom: String
)

data class UpdateTariffRequest(
    val provider: String,
    val planType: String,
    val singleRate: Double,
    val dayRate: Double,
    val nightRate: Double
)