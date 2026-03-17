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
<<<<<<< HEAD
=======
import com.allan88.journeymanager.ui.admin.AdminTrackingScreen
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
import com.allan88.journeymanager.ui.trip.TripScreen
import com.allan88.journeymanager.ui.common.RoleSelectionScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

<<<<<<< HEAD
        // Attempt login when app starts
=======
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
        lifecycleScope.launch {

            try {

                Log.d("AUTH", "Attempting login...")

<<<<<<< HEAD
                val token = ApiClient.apiService.login(
                    mapOf(
                        "email" to "user@tenant1.com",
=======
                // ✅ FIX: Expect ApiResponse<String>
                val response = ApiClient.apiService.login(
                    mapOf(
                        "email" to "admin@tenant1.com",
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
                        "password" to "password"
                    )
                )

<<<<<<< HEAD
=======
                val token = response.data

>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
                if (!token.isNullOrBlank()) {

                    TokenManager.saveToken(token)

                    Log.d("AUTH", "TOKEN SAVED: $token")

                } else {

<<<<<<< HEAD
                    Log.e("AUTH", "TOKEN RECEIVED BUT EMPTY")

=======
                    Log.e("AUTH", "TOKEN NULL OR EMPTY")
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
                }

            } catch (e: Exception) {

<<<<<<< HEAD
                Log.e("AUTH", "LOGIN FAILED", e)

            }

            // Load UI after login attempt
=======
                Log.e("AUTH", "LOGIN FAILED: ${e.message}")

                // ❗ Do NOT crash app
            }

            // ✅ UI loads AFTER login attempt
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
            setContent {
                MainMenu()
            }
        }
    }
}

@Composable
fun MainMenu() {

    var screen by remember { mutableStateOf("role") }
<<<<<<< HEAD
=======
    var trackingTripId by remember { mutableStateOf<Long?>(null) }
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)

    when (screen) {

        "role" -> RoleSelectionScreen(
<<<<<<< HEAD
            onUserSelected = {
                screen = "userTrips"
            },
            onAdminSelected = {
=======

            onUserSelected = {
                screen = "userTrips"
            },

            onAdminSelected = {

                val token = TokenManager.getToken()

                if (token.isNullOrBlank()) {

                    Log.e("AUTH", "BLOCK ADMIN — TOKEN NOT READY")

                    return@RoleSelectionScreen
                }

                Log.d("AUTH", "TOKEN OK — OPEN ADMIN PANEL")

>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
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
<<<<<<< HEAD
            }
        )
=======
            },
            onLiveTracking = { tripId ->

                val token = TokenManager.getToken()

                if (token.isNullOrBlank()) {

                    Log.e("AUTH", "BLOCK TRACKING — TOKEN NOT READY")
                    return@AdminTripScreen
                }

                trackingTripId = tripId
                screen = "adminTracking"
            }
        )

        "adminTracking" -> {

            val tripId = trackingTripId

            if (tripId != null) {

                AdminTrackingScreen(
                    tripId = tripId,
                    onBack = { screen = "adminTrips" }
                )

            } else {

                Log.e("NAVIGATION", "TripId NULL — returning")

                screen = "adminTrips"
            }
        }
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    }
}