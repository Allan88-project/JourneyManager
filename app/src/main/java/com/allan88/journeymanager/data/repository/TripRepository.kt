package com.allan88.journeymanager.data.repository

import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.network.ApiService

class TripRepository(
    private val apiService: ApiService
) {

    suspend fun getTrips(): List<Trip> {
        return apiService.getTrips().data
    }

    suspend fun submitTrip(data: Map<String, String>): Trip {
        return apiService.createTrip(data).data
    }

    suspend fun approveTrip(id: Long) {
        apiService.approveTrip(id)
    }

    suspend fun rejectTrip(id: Long) {
        apiService.rejectTrip(id)
    }

    suspend fun startJourney(id: Long) {
        apiService.startTrip(id)
    }

    suspend fun completeJourney(id: Long) {
        apiService.completeTrip(id)
    }

    suspend fun emergency(id: Long) {
        apiService.emergencyTrip(id)
    }
}