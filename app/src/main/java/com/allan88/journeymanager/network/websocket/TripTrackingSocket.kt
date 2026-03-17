package com.allan88.journeymanager.network.websocket

import okhttp3.*
import org.json.JSONObject

class TripTrackingSocket(
    private val url: String,
    private val onLocationUpdate: (Double, Double) -> Unit
) {

    private val client = OkHttpClient()
    private lateinit var webSocket: WebSocket

    fun connect() {

        val request = Request.Builder()
            .url(url)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onMessage(webSocket: WebSocket, text: String) {

                val json = JSONObject(text)

                val lat = json.getDouble("latitude")
                val lon = json.getDouble("longitude")

                onLocationUpdate(lat, lon)
            }
        })
    }

    fun disconnect() {
        webSocket.close(1000, null)
    }
}