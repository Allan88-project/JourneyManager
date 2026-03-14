package com.allan88.journeymanager.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.allan88.journeymanager.network.ApiService
import com.allan88.journeymanager.data.model.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.location.LocationRequest as GpsLocationRequest

class LocationTrackingManager(
    context: Context,
    private val apiService: ApiService
) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var locationCallback: LocationCallback? = null

    private val locationRequest =
        GpsLocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000
        ).build()

    @SuppressLint("MissingPermission")
    fun startTracking(tripId: Long) {

        Log.d("GPS_TRACKING", "Starting GPS tracking for tripId=$tripId")

        locationCallback = object : LocationCallback() {

            override fun onLocationResult(result: LocationResult) {

                val location = result.lastLocation

                if (location == null) {
                    Log.e("GPS_TRACKING", "Location is NULL")
                    return
                }

                Log.d(
                    "GPS_TRACKING",
                    "Location received: lat=${location.latitude}, lon=${location.longitude}"
                )

                val request = LocationRequest(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    speed = location.speed.toDouble(),
                    heading = location.bearing.toDouble()
                )

                Thread {
                    try {

                        Log.d("GPS_TRACKING", "Sending location to backend...")

                        apiService.updateLocation(tripId, request).execute()

                        Log.d("GPS_TRACKING", "Location sent successfully")

                    } catch (e: Exception) {

                        Log.e("GPS_TRACKING", "Failed to send location: ${e.message}")

                    }
                }.start()
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            Looper.getMainLooper()
        )
    }

    fun stopTracking() {

        Log.d("GPS_TRACKING", "Stopping GPS tracking")

        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
    }
}