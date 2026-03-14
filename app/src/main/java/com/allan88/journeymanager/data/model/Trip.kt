package com.allan88.journeymanager.data.model

data class Trip(

    val id: Long?,

    val tenantId: Long?,

    val title: String?,

    val description: String?,

    val status: String?,

    val createdAt: String?,

    val startedAt: String?,

    val completedAt: String?,

// NEW: navigation coordinates
    val destinationLatitude: Double?,

    val destinationLongitude: Double?

)
