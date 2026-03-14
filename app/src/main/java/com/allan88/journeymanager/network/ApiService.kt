package com.allan88.journeymanager.network

import com.allan88.journeymanager.data.model.AdminAnalyticsResponse
import com.allan88.journeymanager.data.model.LocationRequest
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.data.model.TripAudit
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("api/trips/{id}/timeline")
    suspend fun getTripTimeline(
        @Path("id") tripId: Long
    ): List<TripAudit>

    @GET("api/admin/analytics")
    suspend fun getAdminAnalytics(): AdminAnalyticsResponse

    /*
     * GPS LOCATION UPDATE
     */

    @POST("api/trips/{tripId}/location")
    fun updateLocation(
        @Path("tripId") tripId: Long,
        @Body request: LocationRequest
    ): Call<Void>

    /*
     * AUTH
     */

    @POST("api/auth/login")
    suspend fun login(
        @Body body: Map<String, String>
    ): String

    /*
     * TRIPS
     */

    @GET("api/trips")
    suspend fun getTrips(): ApiResponse<List<Trip>>

    @POST("api/trips")
    suspend fun createTrip(
        @Body body: Map<String, String>
    ): ApiResponse<Trip>

    @PUT("api/trips/{id}/approve")
    suspend fun approveTrip(
        @Path("id") id: Long
    ): ApiResponse<Trip>

    @PUT("api/trips/{id}/reject")
    suspend fun rejectTrip(
        @Path("id") id: Long
    ): ApiResponse<Trip>

    @PUT("api/trips/{id}/start")
    suspend fun startTrip(
        @Path("id") id: Long
    ): ApiResponse<Trip>

    @PUT("api/trips/{id}/complete")
    suspend fun completeTrip(
        @Path("id") id: Long
    ): ApiResponse<Trip>

    @PUT("api/trips/{id}/emergency")
    suspend fun emergencyTrip(
        @Path("id") id: Long
    ): ApiResponse<Trip>
}