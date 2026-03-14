package com.allan88.journeymanager.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    onTrips: () -> Unit,
    onAdmin: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Button(
            onClick = onTrips,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Trips")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAdmin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Admin Panel")
        }
    }
}