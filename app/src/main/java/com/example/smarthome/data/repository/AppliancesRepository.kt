package com.example.smarthome.data.repository

import com.example.smarthome.data.api.RetrofitClient
import com.example.smarthome.data.model.AddApplianceRequest
import com.example.smarthome.data.model.Appliance

class AppliancesRepository {
    private val api = RetrofitClient.api

    suspend fun getAppliances(): List<Appliance> {
        return api.getAppliances()
    }

    suspend fun addAppliance(
        name: String,
        icon: String,
        wattTypical: Int,
        hoursPerDay: Double
    ): Appliance {
        return api.addAppliance(
            AddApplianceRequest(
                name = name,
                icon = icon,
                wattTypical = wattTypical,
                hoursPerDay = hoursPerDay
            )
        )
    }
}