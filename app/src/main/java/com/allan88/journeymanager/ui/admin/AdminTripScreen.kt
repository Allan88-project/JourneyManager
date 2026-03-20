package com.allan88.journeymanager.ui.admin

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.data.repository.TripRepository
import com.allan88.journeymanager.network.ApiClient
import com.allan88.journeymanager.network.websocket.WebSocketManager
import com.allan88.journeymanager.viewmodel.TripViewModel
import com.allan88.journeymanager.ui.trip.TripItem
import com.google.gson.JsonParser
import kotlinx.coroutines.delay
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.*

@Composable
fun DashboardCard(
    title: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value.toString())
        }
    }
}

@Composable
fun AdminTripScreen(onBack: () -> Unit) {

    val context = LocalContext.current

    val prefs = remember {
        context.getSharedPreferences("admin_prefs", Context.MODE_PRIVATE)
    }

    val repository = remember { TripRepository(ApiClient.apiService) }
    val webSocketManager = remember { WebSocketManager() }
    val viewModel = remember { TripViewModel(repository) }

    val trips by viewModel.trips.collectAsState()

    var emergencyMessage by remember { mutableStateOf<String?>(null) }
    var lastEmergencyTripId by remember { mutableStateOf<Long?>(null) }

    var lastShownEmergencyId by remember {
        mutableStateOf(
            prefs.getLong("last_shown_emergency_id", -1L)
                .takeIf { it != -1L }
        )
    }

    var emergencyLocation by remember { mutableStateOf<LatLng?>(null) }

    var selectedTab by remember { mutableStateOf(0) }
    var showHistory by remember { mutableStateOf(false) }

    val hiddenTripIds = remember {
        mutableStateListOf<Long>().apply {
            val saved = prefs.getString("hidden_trip_ids_admin", "") ?: ""
            if (saved.isNotEmpty()) {
                addAll(saved.split(",").mapNotNull { it.toLongOrNull() })
            }
        }
    }

    LaunchedEffect(Unit) {

        webSocketManager.connect("ws://192.168.1.5:8081/ws") { message ->

            try {
                val json = JsonParser().parse(message).asJsonObject

                if (json.has("status") &&
                    json.get("status").asString == "EMERGENCY") {

                    val tripId = json.get("tripId").asLong

                    if (tripId != lastShownEmergencyId) {
                        lastEmergencyTripId = tripId
                        emergencyMessage = "🚨 EMERGENCY ALERT\nTrip ID: $tripId"
                        emergencyLocation = LatLng(3.1390, 101.6869)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModel.loadTrips()

        while (true) {
            delay(3000)
            viewModel.loadTrips()
        }
    }

    LaunchedEffect(trips) {

        val latestEmergency = trips
            .filter { it.status.equals("EMERGENCY", true) }
            .maxByOrNull { it.id ?: -1 }

        val latestId = latestEmergency?.id

        if (latestId != null && latestId != lastShownEmergencyId) {
            lastEmergencyTripId = latestId
            emergencyMessage = "🚨 EMERGENCY ALERT\nTrip ID: $latestId"
            emergencyLocation = LatLng(3.1390, 101.6869)
        }
    }

    DisposableEffect(Unit) {
        onDispose { webSocketManager.disconnect() }
    }

    val pending = trips.count { it.status.equals("PENDING", true) }
    val approved = trips.count { it.status.equals("APPROVED", true) }
    val rejected = trips.count { it.status.equals("REJECTED", true) }
    val inProgress = trips.count { it.status.equals("IN_PROGRESS", true) }
    val completed = trips.count { it.status.equals("COMPLETED", true) }
    val emergency = trips.count { it.status.equals("EMERGENCY", true) }

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

    val defaultLocation = LatLng(3.1390, 101.6869)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 10f)
    }

    if (emergencyMessage != null) {
        AlertDialog(
            onDismissRequest = { emergencyMessage = null },
            confirmButton = {
                Button(onClick = {

                    lastShownEmergencyId = lastEmergencyTripId

                    prefs.edit()
                        .putLong("last_shown_emergency_id", lastEmergencyTripId ?: -1L)
                        .apply()

                    emergencyMessage = null

                    emergencyLocation?.let {
                        cameraPositionState.position =
                            CameraPosition.fromLatLngZoom(it, 15f)
                    }
                }) { Text("OK") }
            },
            title = { Text("EMERGENCY ALERT", color = Color.Red) },
            text = { Text(emergencyMessage!!) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        Button(onClick = onBack) { Text("BACK") }

        Spacer(modifier = Modifier.height(12.dp))

        Text("ADMIN PANEL", color = Color.Red, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TabRow(selectedTabIndex = selectedTab) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                Text("Dashboard")
            }
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                Text("Trips")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {

            0 -> {
                Column {

                    Row {
                        DashboardCard("Pending", pending, Modifier.weight(1f))
                        DashboardCard("Approved", approved, Modifier.weight(1f))
                    }

                    Row {
                        DashboardCard("Rejected", rejected, Modifier.weight(1f))
                        DashboardCard("In Progress", inProgress, Modifier.weight(1f))
                    }

                    Row {
                        DashboardCard("Completed", completed, Modifier.weight(1f))
                        DashboardCard("Emergency", emergency, Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Live Map", color = Color.White)

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = MarkerState(position = defaultLocation),
                            title = "Default Location"
                        )

                        // ✅ STEP 1: STATIC MARKERS ONLY
                        trips
                            .filter { it.status.equals("IN_PROGRESS", true) }
                            .forEach { trip ->

                                val lat = trip.destinationLatitude
                                val lng = trip.destinationLongitude

                                if (lat != null && lng != null) {
                                    Marker(
                                        state = MarkerState(
                                            position = LatLng(lat, lng)
                                        ),
                                        title = "Trip ID: ${trip.id}"
                                    )
                                }
                            }
                    }
                }
            }

            1 -> {
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
                                isAdmin = true
                            )

                            if (showHistory) {
                                Button(
                                    onClick = {
                                        trip.id?.let { id ->
                                            hiddenTripIds.add(id)

                                            prefs.edit()
                                                .putString(
                                                    "hidden_trip_ids_admin",
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
    }
}