package com.allan88.journeymanager.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.allan88.journeymanager.viewmodel.AdminTrackingViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun AdminTrackingScreen(

    tripId: Long,
    onBack: () -> Unit,
    viewModel: AdminTrackingViewModel = viewModel()

) {

    val location by viewModel.vehicleLocation.collectAsState()

    // ✅ Start map at Malaysia instead of (0,0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(3.1390, 101.6869), // Kuala Lumpur fallback
            12f
        )
    }

    /*
     * CONNECT WEBSOCKET ONCE
     */
    LaunchedEffect(tripId) {
        viewModel.connectWebSocket(tripId)
    }

    /*
     * DISCONNECT SAFELY
     */
    DisposableEffect(Unit) {
        onDispose {
            viewModel.disconnect()
        }
    }

    /*
     * ✅ SMOOTH CAMERA TRACKING
     */
    LaunchedEffect(location) {
        location?.let {

            cameraPositionState.animate(
                CameraUpdateFactory.newLatLng(it), // smooth follow
                1000 // duration ms
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(onClick = onBack) {
                Text("BACK")
            }

            Text(
                text = "LIVE TRACKING",
                color = Color.White
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {

                /*
                 * VEHICLE MARKER
                 */
                location?.let { position ->

                    Marker(
                        state = MarkerState(position),
                        title = "Vehicle"
                    )

                }

                /*
                 * ROUTE LINE
                 */
                if (viewModel.route.isNotEmpty()) {

                    Polyline(
                        points = viewModel.route.toList()
                    )

                }

            }

        }

    }
}