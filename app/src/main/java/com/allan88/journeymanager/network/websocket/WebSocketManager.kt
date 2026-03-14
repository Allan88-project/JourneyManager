package com.allan88.journeymanager.network.websocket

import okhttp3.*
import okio.ByteString

class WebSocketManager {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect(url: String, onMessage: (String) -> Unit) {

        val request = Request.Builder()
            .url(url)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
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
            }
        })
    }

    fun disconnect() {
        webSocket?.close(1000, "Closed")
    }

}
