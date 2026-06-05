package com.example.smarthome.data.repository

import com.example.smarthome.data.api.RetrofitClient
import com.example.smarthome.data.model.BillingPeriod
import com.example.smarthome.data.model.CurrentMonthBilling

class BillingRepository {
    private val api = RetrofitClient.api

    suspend fun getBillingPeriods(): List<BillingPeriod> {
        return api.getBillingPeriods()
    }

    suspend fun getCurrentMonth(): CurrentMonthBilling {
        return api.getCurrentMonth()
    }
}