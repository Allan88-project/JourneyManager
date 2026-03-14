package com.allan88.journeymanager.ui.trip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allan88.journeymanager.data.repository.TripRepository
import com.allan88.journeymanager.network.ApiClient
import com.allan88.journeymanager.network.TokenManager
import com.allan88.journeymanager.viewmodel.TripViewModel
import kotlinx.coroutines.delay

@Composable
fun TripScreen(
    onBack: () -> Unit
) {

    val repository = remember {
        TripRepository(ApiClient.apiService)
    }

    val viewModel = remember {
        TripViewModel(repository)
    }

    val trips by viewModel.trips.collectAsState()

    LaunchedEffect(Unit) {

        // Wait until JWT token exists
        while (TokenManager.getToken() == null) {
            delay(100)
        }

        viewModel.loadTrips()
    }

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
            text = "Journey Manager Console",
            color = Color.Green,
            fontSize = 22.sp
        )

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

        Text(
            text = "Trips Loaded: ${trips.size}",
            color = Color.Green
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(trips) { trip ->

                TripItem(
                    trip = trip,
                    viewModel = viewModel,
                    isAdmin = false   // USER VIEW
                )

            }
        }
    }
}