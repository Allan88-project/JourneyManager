package com.allan88.journeymanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.data.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TripViewModel(private val repository: TripRepository) : ViewModel() {

    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips

    fun loadTrips() {
        viewModelScope.launch {
            try {
                _trips.value = repository.getTrips()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun submitTrip(data: Map<String, String>) {

        viewModelScope.launch {

            try {

                println("Submitting trip...")

                repository.submitTrip(data)

                println("Trip created successfully")

                loadTrips()

            } catch (e: Exception) {

                println("Trip creation FAILED")
                e.printStackTrace()
            }
        }
    }

    // =========================
    // ADMIN ACTIONS
    // =========================

    fun approveTrip(tripId: Long) {
        viewModelScope.launch {
            try {
                repository.approveTrip(tripId)
                loadTrips()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun rejectTrip(tripId: Long) {
        viewModelScope.launch {
            try {
                repository.rejectTrip(tripId)
                loadTrips()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // =========================
    // USER ACTIONS
    // =========================

    fun startJourney(tripId: Long) {
        viewModelScope.launch {
            try {
                repository.startJourney(tripId)
                loadTrips()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun emergency(tripId: Long) {
        viewModelScope.launch {
            try {
                repository.emergency(tripId)
                loadTrips()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun completeJourney(tripId: Long) {
        viewModelScope.launch {
            try {
                repository.completeJourney(tripId)
                loadTrips()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}