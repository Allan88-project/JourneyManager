package com.allan88.journeymanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.allan88.journeymanager.network.ApiClient
import com.allan88.journeymanager.network.TokenManager
import com.allan88.journeymanager.ui.admin.AdminTripScreen
import com.allan88.journeymanager.ui.trip.TripScreen
import com.allan88.journeymanager.ui.common.RoleSelectionScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApiClient.init(application)
        // Attempt login when app starts
        lifecycleScope.launch {

            try {

                Log.d("AUTH", "Attempting login...")

                val response = ApiClient.apiService.login(
                    mapOf(
                        "email" to "admin@tenant1.com",
                        "password" to "password"
                    )
                )

                val token = response.substringAfter("\"data\":\"").substringBefore("\"")

                if (token.isNotBlank()) {

                    val tokenManager = TokenManager(applicationContext)
                    tokenManager.saveToken(token)

                    Log.d("AUTH", "TOKEN SAVED: $token")

                } else {
                    Log.e("AUTH", "TOKEN EXTRACTION FAILED")
                }

            } catch (e: Exception) {

                Log.e("AUTH", "LOGIN FAILED", e)

            }

            // Load UI after login attempt
            setContent {
                MainMenu()
            }
        }
    }
}

@Composable
fun MainMenu() {

    var screen by remember { mutableStateOf("role") }

    when (screen) {

        "role" -> RoleSelectionScreen(
            onUserSelected = {
                screen = "userTrips"
            },
            onAdminSelected = {
                screen = "adminTrips"
            }
        )

        "userTrips" -> TripScreen(
            onBack = {
                screen = "role"
            }
        )

        "adminTrips" -> AdminTripScreen(
            onBack = {
                screen = "role"
            }
        )
    }
}