package com.allan88.journeymanager.ui.trip

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.allan88.journeymanager.data.repository.TripRepository
import com.allan88.journeymanager.network.ApiClient
import com.allan88.journeymanager.network.TokenManager
import com.allan88.journeymanager.viewmodel.TripViewModel
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.Arrangement

@Composable
fun TripScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val prefs = remember {
        context.getSharedPreferences("trip_prefs", Context.MODE_PRIVATE)
    }

    val repository = remember {
        TripRepository(ApiClient.apiService)
    }

    val viewModel = remember {
        TripViewModel(repository)
    }

    val trips by viewModel.trips.collectAsState()

    val tokenManager = remember {
        TokenManager(context)
    }

    var showHistory by remember { mutableStateOf(false) }

    // ✅ PERSISTENT DELETE STATE (CSV)
    val hiddenTripIds = remember {
        mutableStateListOf<Long>().apply {
            val saved = prefs.getString("hidden_trip_ids", "") ?: ""
            if (saved.isNotEmpty()) {
                addAll(saved.split(",").mapNotNull { it.toLongOrNull() })
            }
        }
    }

    LaunchedEffect(Unit) {
        while (tokenManager.getToken().isNullOrEmpty()) {
            delay(100)
        }
        viewModel.loadTrips()
    }

    val filteredTrips = (if (showHistory) {
        trips.filter {
            it.status.equals("COMPLETED", true) ||
                    it.status.equals("REJECTED", true) ||
                    it.status.equals("EMERGENCY", true)
        }
    } else {
        trips.filter {
            it.status.equals("PENDING", true) ||
                    it.status.equals("APPROVED", true) ||
                    it.status.equals("IN_PROGRESS", true)
        }
    }).filter { trip ->
        trip.id != null && !hiddenTripIds.contains(trip.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        Button(onClick = onBack) { Text("BACK") }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Journey Manager Console", color = Color.Green, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.submitTrip(
                    mapOf(
                        "title" to "Test Trip",
                        "description" to "Office to Client Site",
                        "destinationLatitude" to "3.1390",
                        "destinationLongitude" to "101.6869"
                    )
                )
            }
        ) {
            Text("SUBMIT TEST TRIP")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Trips Loaded: ${trips.size}", color = Color.Green)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { showHistory = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!showHistory) Color.Green else Color.Gray
                )
            ) { Text("Active") }

            Button(
                onClick = { showHistory = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (showHistory) Color.Green else Color.Gray
                )
            ) { Text("History") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(filteredTrips) { trip ->

                Column {

                    TripItem(
                        trip = trip,
                        viewModel = viewModel,
                        isAdmin = false
                    )

                    if (showHistory) {
                        Button(
                            onClick = {
                                trip.id?.let { id ->
                                    hiddenTripIds.add(id)

                                    prefs.edit()
                                        .putString(
                                            "hidden_trip_ids",
                                            hiddenTripIds.joinToString(",")
                                        )
                                        .apply()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            Text("DELETE")
                        }
                    }
                }
            }
        }
    }
}