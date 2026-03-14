package com.allan88.journeymanager.data.model

data class TripAudit(
    val id: Long,
    val tripId: Long,
    val action: String,
    val performedBy: String,
    val role: String,
    val timestamp: String
)

