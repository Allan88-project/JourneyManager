package com.allan88.journeymanager.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
<<<<<<< HEAD
import retrofit2.converter.scalars.ScalarsConverterFactory
=======
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)

object ApiClient {

    private const val BASE_URL = "http://192.168.1.5:8081/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

<<<<<<< HEAD
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)

        // IMPORTANT ORDER
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
=======
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())   // ✅ CRITICAL
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)            // ✅ USE CLIENT
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    }
}