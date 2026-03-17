package com.allan88.journeymanager.viewmodel

import androidx.lifecycle.ViewModel
import com.allan88.journeymanager.network.websocket.WebSocketManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject

class AdminTrackingViewModel : ViewModel() {

    private val webSocketManager = WebSocketManager()

    private val _vehicleLocation = MutableStateFlow<LatLng?>(null)
    val vehicleLocation: StateFlow<LatLng?> = _vehicleLocation

    val route = mutableListOf<LatLng>()

    fun connectWebSocket(tripId: Long) {

        webSocketManager.connect(
            "ws://192.168.1.5:8081/ws" // ⚠️ ensure this matches your PC IP
        ) { message ->

            println("WS MESSAGE: $message")

            try {
                val json = JSONObject(message)

                val incomingTripId = json.optLong("tripId")

                if (incomingTripId == tripId || incomingTripId == 0L) {

                    val lat = json.getDouble("latitude")
                    val lon = json.getDouble("longitude")

                    val position = LatLng(lat, lon)

                    route.add(position)
                    _vehicleLocation.value = position
                }

            } catch (e: Exception) {
                println("PARSE ERROR: ${e.message}")
            }
        }
    }

    fun disconnect() {
        webSocketManager.disconnect()
    }
}