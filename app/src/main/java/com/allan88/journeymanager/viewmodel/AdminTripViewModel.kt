package com.allan88.journeymanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.data.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminTripViewModel(
    private val repository: TripRepository
) : ViewModel() {

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

    fun approveTrip(tripId: Long) {
        viewModelScope.launch {
            repository.approveTrip(tripId)
            loadTrips()
        }
    }

    fun rejectTrip(tripId: Long) {
        viewModelScope.launch {
            repository.rejectTrip(tripId)
            loadTrips()
        }
    }
}