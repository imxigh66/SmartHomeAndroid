package com.example.smarthome.data.repository

import com.example.smarthome.data.api.RetrofitClient
import com.example.smarthome.data.model.AddReadingRequest
import com.example.smarthome.data.model.MeterReading
import java.time.LocalDate

class ReadingsRepository {
    private val api = RetrofitClient.api

    suspend fun getReadings():List<MeterReading>{
        return api.getReadings()
    }

    suspend fun getLatestReading(): MeterReading{
        return api.getLatestReading()
    }

    suspend fun addReading(
        dayReading: Double,
        nightReading: Double,
        isTwoZone: Boolean
    ): MeterReading {
        return api.addReading(
            AddReadingRequest(
                readingDate = LocalDate.now().toString(),
                dayReading = dayReading,
                nightReading = nightReading,
                isTwoZone = isTwoZone
            )
        )
    }
}