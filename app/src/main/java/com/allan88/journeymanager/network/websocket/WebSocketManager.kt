package com.allan88.journeymanager.network.websocket

import okhttp3.*
import okio.ByteString

class WebSocketManager {

<<<<<<< HEAD
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect(url: String, onMessage: (String) -> Unit) {
=======
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()

    fun connect(url: String, onMessageReceived: (String) -> Unit) {
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)

        val request = Request.Builder()
            .url(url)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
<<<<<<< HEAD
                println("WebSocket Connected")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                onMessage(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                onMessage(bytes.utf8())
            }

            override fun onFailure(
                webSocket: WebSocket,
                t: Throwable,
                response: Response?
            ) {
                println("WebSocket Error: ${t.message}")
=======
                println("WS CONNECTED")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                println("WS MESSAGE: $text")
                onMessageReceived(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                val text = bytes.utf8()
                println("WS MESSAGE (bytes): $text")
                onMessageReceived(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                println("WS CLOSING: $reason")
                webSocket.close(1000, null)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("WS ERROR: ${t.message}")
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
            }
        })
    }

    fun disconnect() {
<<<<<<< HEAD
        webSocket?.close(1000, "Closed")
    }

}
=======
        webSocket?.close(1000, "Closed by user")
        webSocket = null
        println("WS DISCONNECTED")
    }
}
>>>>>>> f3ac6ea (Milestone: Live GPS Tracking + Admin Map + JWT Auth stable)
