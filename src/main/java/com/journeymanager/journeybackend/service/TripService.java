package com.journeymanager.journeybackend.service;

import com.journeymanager.journeybackend.model.trip.Trip;
import com.journeymanager.journeybackend.model.trip.TripStatus;
import com.journeymanager.journeybackend.repository.TripRepository;
import com.journeymanager.journeybackend.security.CustomUserDetails;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    /*
     * TENANT CONTEXT
     */

    private Long getCurrentTenantId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        return user.getTenantId();
    }

    /*
     * BASIC OPERATIONS
     */

    public List<Trip> findAll() {

        Long tenantId = getCurrentTenantId();

        return tripRepository.findByTenantId(tenantId);
    }

    public Trip create(Trip trip) {
        return tripRepository.save(trip);
    }

    /*
     * TENANT SAFE TRIP FETCH
     */

    private Trip getTripOrThrow(Long id) {

        Long tenantId = getCurrentTenantId();

        return tripRepository
                .findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));
    }

    /*
     * ADMIN ACTIONS
     */

    public Trip approveTrip(Long tripId) {

        Trip trip = getTripOrThrow(tripId);

        if (trip.getStatus() != TripStatus.PENDING) {
            throw new IllegalStateException("Only PENDING trips can be approved");
        }

        trip.setStatus(TripStatus.APPROVED);

        return tripRepository.save(trip);
    }

    public Trip rejectTrip(Long tripId) {

        Trip trip = getTripOrThrow(tripId);

        if (trip.getStatus() != TripStatus.PENDING) {
            throw new IllegalStateException("Only PENDING trips can be rejected");
        }

        trip.setStatus(TripStatus.REJECTED);

        return tripRepository.save(trip);
    }

    /*
     * USER ACTIONS
     */

    public Trip startTrip(Long tripId) {

        Trip trip = getTripOrThrow(tripId);

        if (trip.getStatus() != TripStatus.APPROVED) {
            throw new IllegalStateException("Trip must be APPROVED before starting");
        }

        trip.setStatus(TripStatus.IN_PROGRESS);
        trip.setStartedAt(LocalDateTime.now());

        return tripRepository.save(trip);
    }

    public Trip completeTrip(Long tripId) {

        Trip trip = getTripOrThrow(tripId);

        if (trip.getStatus() != TripStatus.IN_PROGRESS) {
            throw new IllegalStateException("Only IN_PROGRESS trips can be completed");
        }

        trip.setStatus(TripStatus.COMPLETED);
        trip.setCompletedAt(LocalDateTime.now());

        return tripRepository.save(trip);
    }

    public Trip emergencyTrip(Long tripId) {

        Trip trip = getTripOrThrow(tripId);

        if (trip.getStatus() != TripStatus.IN_PROGRESS) {
            throw new IllegalStateException("Emergency can only occur during IN_PROGRESS");
        }

        trip.setStatus(TripStatus.EMERGENCY);

        return tripRepository.save(trip);
    }
}