package com.allan88.journeymanager.ui.trip

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.location.LocationTrackingManager
<<<<<<< HEAD
import com.allan88.journeymanager.network.ApiClient
=======
import com.allan88.journeymanager.network.TokenManager
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
import com.allan88.journeymanager.viewmodel.TripViewModel

@Composable
fun TripItem(
    trip: Trip,
    viewModel: TripViewModel,
    isAdmin: Boolean
) {

    val context = LocalContext.current

    val trackingManager = remember {
<<<<<<< HEAD
        LocationTrackingManager(context, ApiClient.apiService)
=======
        LocationTrackingManager(context)
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {
<<<<<<< HEAD
                trip.id?.let { trackingManager.startTracking(it) }
=======

                val token = TokenManager.getToken()

                if (token.isNullOrBlank()) {
                    Log.e("GPS_TRACKING", "TOKEN NOT READY — BLOCK GPS START")
                    return@rememberLauncherForActivityResult
                }

                trip.id?.let { trackingManager.startTracking(it) }

>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
            } else {
                Log.e("GPS_TRACKING", "Location permission denied")
            }
        }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(text = trip.title ?: "Untitled Trip")

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = trip.description ?: "")

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Status: ${trip.status}")

            Spacer(modifier = Modifier.height(12.dp))

            /*
             * ADMIN ACTIONS
             */
<<<<<<< HEAD

=======
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
            if (isAdmin && trip.status == "PENDING") {

                Row {

                    Button(
                        onClick = {
                            trip.id?.let { id ->
                                viewModel.approveTrip(id)
                                Log.d("ADMIN", "Trip approved: $id")
                            }
                        }
                    ) {
                        Text("Approve")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            trip.id?.let { id ->
                                viewModel.rejectTrip(id)
                                Log.d("ADMIN", "Trip rejected: $id")
                            }
                        }
                    ) {
                        Text("Reject")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            /*
             * USER START JOURNEY
             */
<<<<<<< HEAD

=======
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
            if (!isAdmin && trip.status == "APPROVED") {

                Button(
                    onClick = {

<<<<<<< HEAD
=======
                        val token = TokenManager.getToken()

                        if (token.isNullOrBlank()) {
                            Log.e("GPS_TRACKING", "TOKEN NOT READY — BLOCK START")
                            return@Button
                        }

>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
                        trip.id?.let { id ->

                            viewModel.startJourney(id)

                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {

                                Log.d("GPS_TRACKING", "Permission already granted")
                                trackingManager.startTracking(id)

                            } else {

                                Log.d("GPS_TRACKING", "Requesting location permission")
                                permissionLauncher.launch(
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                )
                            }

                            val lat = trip.destinationLatitude
                            val lon = trip.destinationLongitude

                            if (lat != null && lon != null) {
<<<<<<< HEAD

                                openGoogleMaps(context, lat, lon)

                            } else {

=======
                                openGoogleMaps(context, lat, lon)
                            } else {
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
                                Log.e("NAVIGATION", "Trip has no coordinates")
                            }
                        }
                    }
                ) {
                    Text("Start Journey")
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            /*
             * TRIP IN PROGRESS
             */
<<<<<<< HEAD

=======
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
            if (!isAdmin && trip.status == "IN_PROGRESS") {

                Column {

                    Button(
                        onClick = {

                            val lat = trip.destinationLatitude
                            val lon = trip.destinationLongitude

                            if (lat != null && lon != null) {
<<<<<<< HEAD

                                openGoogleMaps(context, lat, lon)

                            } else {

=======
                                openGoogleMaps(context, lat, lon)
                            } else {
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
                                Log.e("NAVIGATION", "Trip has no coordinates")
                            }
                        }
                    ) {
                        Text("Resume Navigation")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {

                        Button(
                            onClick = {
                                trip.id?.let { id ->
                                    viewModel.emergency(id)
                                    trackingManager.stopTracking()
                                }
                            }
                        ) {
                            Text("Emergency")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                trip.id?.let { id ->
                                    viewModel.completeJourney(id)
                                    trackingManager.stopTracking()
                                }
                            }
                        ) {
                            Text("End Journey")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            /*
             * FINAL STATES
             */
<<<<<<< HEAD

=======
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
            if (trip.status == "COMPLETED") {
                Text("Journey Completed")
            }

            if (trip.status == "REJECTED") {
                Text("Trip Rejected")
            }
        }
    }
}

fun openGoogleMaps(
    context: Context,
    lat: Double,
    lng: Double
) {

    val uri = Uri.parse("google.navigation:q=$lat,$lng")

    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.google.android.apps.maps")

    if (intent.resolveActivity(context.packageManager) != null) {
<<<<<<< HEAD

        context.startActivity(intent)

    } else {

=======
        context.startActivity(intent)
    } else {
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
        val fallback = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$lat,$lng")
        )
<<<<<<< HEAD

=======
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
        context.startActivity(fallback)
    }
}