
package com.journeymanager.journeybackend.service;

import com.journeymanager.journeybackend.exception.AccessDeniedException;
import com.journeymanager.journeybackend.model.trip.Trip;
import com.journeymanager.journeybackend.model.trip.TripStatus;
import com.journeymanager.journeybackend.repository.TripRepository;
import com.journeymanager.journeybackend.security.RoleContext;
import com.journeymanager.journeybackend.security.UserRole;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    // USER create trip
    public Trip create(Trip trip) {
        return tripRepository.save(trip);
    }

    // list trips
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    // ADMIN approve / reject
    public Trip updateStatus(Long tripId, TripStatus newStatus) {

        if (RoleContext.getRole() != UserRole.ADMIN) {
            throw new AccessDeniedException("Only ADMIN can approve or reject trips");
        }

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (trip.getStatus() != TripStatus.PENDING) {
            throw new IllegalStateException("Trip already finalized");
        }

        trip.setStatus(newStatus);

        return tripRepository.save(trip);
    }

    // USER start journey
    public Trip startJourney(Long tripId) {

        if (RoleContext.getRole() != UserRole.USER) {
            throw new AccessDeniedException("Only USER can start journey");
        }

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (trip.getStatus() != TripStatus.APPROVED) {
            throw new IllegalStateException("Trip must be APPROVED to start");
        }

        trip.setStatus(TripStatus.IN_PROGRESS);
        trip.setStartedAt(LocalDateTime.now());

        return tripRepository.save(trip);
    }

    // USER emergency
    public Trip triggerEmergency(Long tripId) {

        if (RoleContext.getRole() != UserRole.USER) {
            throw new AccessDeniedException("Only USER can trigger emergency");
        }

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (trip.getStatus() != TripStatus.IN_PROGRESS) {
            throw new IllegalStateException("Emergency allowed only during IN_PROGRESS");
        }

        trip.setStatus(TripStatus.EMERGENCY);

        return tripRepository.save(trip);
    }

    // USER complete journey
    public Trip completeJourney(Long tripId) {

        if (RoleContext.getRole() != UserRole.USER) {
            throw new AccessDeniedException("Only USER can complete journey");
        }

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (trip.getStatus() != TripStatus.IN_PROGRESS &&
                trip.getStatus() != TripStatus.EMERGENCY) {
            throw new IllegalStateException("Trip must be IN_PROGRESS or EMERGENCY to complete");
        }

        trip.setStatus(TripStatus.COMPLETED);
        trip.setCompletedAt(LocalDateTime.now());

        return tripRepository.save(trip);
    }
}
