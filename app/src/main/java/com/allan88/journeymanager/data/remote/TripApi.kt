package com.allan88.journeymanager.data.remote

import com.allan88.journeymanager.data.model.Trip
import retrofit2.http.*

interface TripApi {

    @GET("api/trips")
    suspend fun getTrips(): List<Trip>

    @POST("api/trips")
    suspend fun createTrip(
        @Body body: Map<String, String>
    ): Trip

    @PUT("api/trips/{id}/status")
    suspend fun updateStatus(
        @Path("id") id: Long,
        @Query("status") status: String
    ): Trip


    // ================================
    // TRIP LIFECYCLE OPERATIONS
    // ================================

    @POST("api/trips/{id}/approve")
    suspend fun approveTrip(
        @Path("id") id: Long
    ): Trip

    @POST("api/trips/{id}/reject")
    suspend fun rejectTrip(
        @Path("id") id: Long
    ): Trip

    @POST("api/trips/{id}/start")
    suspend fun startTrip(
        @Path("id") id: Long
    ): Trip

    @POST("api/trips/{id}/complete")
    suspend fun completeTrip(
        @Path("id") id: Long
    ): Trip
    @POST("api/trips/{tripId}/location")
    fun updateLocation(
        @Path("tripId") tripId: Long,
        @Body body: Map<String, Double>
    )
    @POST("api/trips/{id}/emergency")
    suspend fun emergencyTrip(
        @Path("id") id: Long
    ): Trip
}