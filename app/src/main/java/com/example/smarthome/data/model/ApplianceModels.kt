package com.example.smarthome.data.model

data class Appliance(
    val id: String,
    val name: String,
    val icon: String,
    val wattTypical: Int,
    val hoursPerDay: Double,
    val monthlyCostLei: Double,
    val percentOfBill: Double,
    val tip: String?
)

data class AddApplianceRequest(
    val name: String,
    val icon: String,
    val wattTypical: Int,
    val hoursPerDay: Double
)