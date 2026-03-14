package com.allan88.journeymanager.network

import okhttp3.Interceptor
import okhttp3.Response

class TenantInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val builder = originalRequest.newBuilder()

        val token = TokenManager.getToken()

        if (token != null) {
            android.util.Log.d("AUTH", "Attaching JWT to request")
            builder.addHeader("Authorization", "Bearer $token")
        }

        val newRequest = builder.build()

        return chain.proceed(newRequest)
    }
}