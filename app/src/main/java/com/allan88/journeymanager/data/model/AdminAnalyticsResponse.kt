package com.allan88.journeymanager.data.model

data class AdminAnalyticsResponse(
    val totalTrips: Long,
    val pending: Long,
    val approved: Long,
    val rejected: Long,
    val inProgress: Long,
    val completed: Long,
    val emergency: Long
)