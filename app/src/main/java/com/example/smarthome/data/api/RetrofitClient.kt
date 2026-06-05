package com.example.smarthome.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL="http://192.168.0.3:5030/api/"

    var token: String=""


    private val logging= HttpLoggingInterceptor().apply {
        level= HttpLoggingInterceptor.Level.BODY
    }


    private val authInterceptor= Interceptor{chain->
        val request=chain.request().newBuilder()
            .addHeader("Authorization","Bearer $token")
            .build()
        chain.proceed(request)

    }
    private val client= OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logging)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}