package com.allan88.journeymanager.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.data.model.AdminAnalyticsResponse
import com.allan88.journeymanager.data.repository.TripRepository
import com.allan88.journeymanager.network.ApiClient
import com.allan88.journeymanager.network.websocket.WebSocketManager
import com.allan88.journeymanager.viewmodel.TripViewModel
import com.allan88.journeymanager.ui.trip.TripItem
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.foundation.layout.Arrangement

@Composable
fun AdminTripScreen(
<<<<<<< HEAD
    onBack: () -> Unit
=======
    onBack: () -> Unit,
    onLiveTracking: (Long) -> Unit
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
) {

    val repository = remember {
        TripRepository(ApiClient.apiService)
    }

    val webSocketManager = remember { WebSocketManager() }

    val viewModel = remember {
        TripViewModel(repository)
    }

    val trips by viewModel.trips.collectAsState()

<<<<<<< HEAD
// Analytics state
=======
    // Analytics state
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    var analytics by remember { mutableStateOf<AdminAnalyticsResponse?>(null) }

    /*
     * WebSocket connection (LIVE updates)
     */
<<<<<<< HEAD

=======
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    LaunchedEffect(Unit) {

        webSocketManager.connect(
            "ws://192.168.1.5:8081/ws"
        ) { message ->

            val updatedAnalytics =
                Gson().fromJson(message, AdminAnalyticsResponse::class.java)

            analytics = updatedAnalytics
        }

        // Initial analytics load
        analytics = withContext(Dispatchers.IO) {
            ApiClient.apiService.getAdminAnalytics()
        }

        viewModel.loadTrips()

        // Polling fallback
        while (true) {

            delay(3000)

            viewModel.loadTrips()

            analytics = withContext(Dispatchers.IO) {
                ApiClient.apiService.getAdminAnalytics()
            }
        }
    }

    /*
     * Close WebSocket when leaving screen
     */
<<<<<<< HEAD

=======
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    DisposableEffect(Unit) {
        onDispose {
            webSocketManager.disconnect()
        }
    }

    /*
     * Dashboard counters
     */
<<<<<<< HEAD

=======
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
    val pending = analytics?.pending ?: 0
    val approved = analytics?.approved ?: 0
    val rejected = analytics?.rejected ?: 0
    val inProgress = analytics?.inProgress ?: 0
    val completed = analytics?.completed ?: 0
    val emergency = analytics?.emergency ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        Button(onClick = onBack) {
            Text("BACK")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "ADMIN PANEL",
            color = Color.Red,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Trips Loaded: ${trips.size}",
            color = Color.Green
        )

        Spacer(modifier = Modifier.height(20.dp))

        /*
         * DASHBOARD
         */
<<<<<<< HEAD

=======
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
        DashboardCard("Pending", pending.toInt())
        DashboardCard("Approved", approved.toInt())
        DashboardCard("Rejected", rejected.toInt())
        DashboardCard("In Progress", inProgress.toInt())
        DashboardCard("Completed", completed.toInt())
        DashboardCard("Emergency", emergency.toInt())

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(trips) { trip: Trip ->

<<<<<<< HEAD
                TripItem(
                    trip = trip,
                    viewModel = viewModel,
                    isAdmin = true
                )
=======
                Column {

                    TripItem(
                        trip = trip,
                        viewModel = viewModel,
                        isAdmin = true
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Button(
                        onClick = { trip.id?.let { onLiveTracking(it) } },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("LIVE TRACKING")
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)

            }

        }
    }

}

@Composable
fun DashboardCard(
    title: String,
    value: Int
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(title)

            Text(value.toString())

        }

    }

<<<<<<< HEAD
}
=======
}
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
