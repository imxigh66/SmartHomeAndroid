package com.example.smarthome.data.api

import com.example.smarthome.data.model.AddApplianceRequest
import com.example.smarthome.data.model.AddReadingRequest
import com.example.smarthome.data.model.Appliance
import com.example.smarthome.data.model.BillingPeriod
import com.example.smarthome.data.model.CurrentMonthBilling
import com.example.smarthome.data.model.DashboardResponse
import com.example.smarthome.data.model.GlobalTariff
import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.data.model.LoginResponse
import com.example.smarthome.data.model.MeterReading
import com.example.smarthome.data.model.MyTariff
import com.example.smarthome.data.model.RegisterRequest
import com.example.smarthome.data.model.UpdateTariffRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService{
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): LoginResponse

    @GET("dashboard")
    suspend fun getDashboard(@Query("userId") userId: String): DashboardResponse

    @GET("appliances")
    suspend fun getAppliances(): List<Appliance>

    @POST("appliances")
    suspend fun addAppliance(@Body request: AddApplianceRequest): Appliance

    @GET("readings")
    suspend fun getReadings(): List<MeterReading>

    @GET("readings/latest")
    suspend fun getLatestReading(): MeterReading

    @POST("readings")
    suspend fun addReading(@Body request: AddReadingRequest): MeterReading

    @GET("billing/periods")
    suspend fun getBillingPeriods(): List<BillingPeriod>

    @GET("billing/current-month")
    suspend fun getCurrentMonth(): CurrentMonthBilling

    @GET("tariffs/my")
    suspend fun getMyTariff(): MyTariff

    @PUT("tariffs/my")
    suspend fun updateTariff(@Body request: UpdateTariffRequest): MyTariff

    @GET("tariffs/global")
    suspend fun getGlobalTariffs(): List<GlobalTariff>
}