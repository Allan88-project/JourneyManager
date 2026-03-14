package com.allan88.journeymanager.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.5:8081/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val newRequest: Request = chain.request()
                .newBuilder()
                .addHeader("X-Tenant-Id", "tenant1")
                .build()
            chain.proceed(newRequest)
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val tripApi: TripApi = retrofit.create(TripApi::class.java)
}