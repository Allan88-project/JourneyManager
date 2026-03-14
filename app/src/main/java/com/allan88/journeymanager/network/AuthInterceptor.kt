package com.allan88.journeymanager.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val token = TokenManager.getToken()

        Log.d("AUTH_INTERCEPTOR", "Interceptor executing")
        Log.d("AUTH_INTERCEPTOR", "Token from manager = $token")

        val requestBuilder = originalRequest.newBuilder()

        if (!token.isNullOrBlank()) {

            requestBuilder.removeHeader("Authorization")

            requestBuilder.addHeader(
                "Authorization",
                "Bearer $token"
            )

            Log.d("AUTH_INTERCEPTOR", "Authorization header attached")

        } else {

            Log.w("AUTH_INTERCEPTOR", "No token available — request sent without JWT")

        }

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}