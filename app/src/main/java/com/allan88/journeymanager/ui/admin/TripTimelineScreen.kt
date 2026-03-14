package com.allan88.journeymanager.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.allan88.journeymanager.data.model.TripAudit
import com.allan88.journeymanager.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun TripTimelineScreen(
    tripId: Long,
    onBack: () -> Unit
) {

    var timeline by remember { mutableStateOf<List<TripAudit>>(emptyList()) }

    LaunchedEffect(Unit) {

        timeline = withContext(Dispatchers.IO) {
            ApiClient.apiService.getTripTimeline(tripId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(onClick = onBack) {
            Text("BACK")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "TRIP TIMELINE",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(timeline) { event ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(event.action)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "By: ${event.performedBy}",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            text = event.timestamp,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }

}
