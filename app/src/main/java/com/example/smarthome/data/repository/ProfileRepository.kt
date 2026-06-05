package com.example.smarthome.data.repository

import com.example.smarthome.data.api.RetrofitClient
import com.example.smarthome.data.model.GlobalTariff
import com.example.smarthome.data.model.MyTariff
import com.example.smarthome.data.model.UpdateTariffRequest

class ProfileRepository {

    private val api = RetrofitClient.api

    suspend fun getMyTariff(): MyTariff {
        return api.getMyTariff()
    }

    suspend fun getGlobalTariffs(): List<GlobalTariff> {
        return api.getGlobalTariffs()
    }

    suspend fun updateTariff(
        provider: String,
        planType: String,
        singleRate: Double,
        dayRate: Double,
        nightRate: Double
    ): MyTariff {
        return api.updateTariff(
            UpdateTariffRequest(
                provider = provider,
                planType = planType,
                singleRate = singleRate,
                dayRate = dayRate,
                nightRate = nightRate
            )
        )
    }
}