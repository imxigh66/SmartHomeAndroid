package com.example.smarthome.data.repository

import com.example.smarthome.data.api.RetrofitClient
import com.example.smarthome.data.model.DashboardResponse

class DashboardRepository {
    private val api = RetrofitClient.api

    suspend fun getDashboard(userId: String): DashboardResponse {
        return api.getDashboard(userId)
    }
}