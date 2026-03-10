package com.journeymanager.journeybackend.controller;

import com.journeymanager.journeybackend.dto.ApiResponse;
import com.journeymanager.journeybackend.model.trip.Trip;
import com.journeymanager.journeybackend.model.trip.TripStatus;
import com.journeymanager.journeybackend.security.CustomUserDetails;
import com.journeymanager.journeybackend.service.TripService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    /*
     * LIST TRIPS (tenant-safe via service layer)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Trip>>> getAll() {

        List<Trip> trips = tripService.findAll();

        return ResponseEntity.ok(
                ApiResponse.success(trips)
        );
    }

    /*
     * DEBUG AUTH (JWT verification)
     */
    @GetMapping("/debug")
    public ResponseEntity<ApiResponse<String>> debugAuth() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.ok(
                    ApiResponse.success("Authentication is NULL")
            );
        }

        String debug = "Principal: " + auth.getPrincipal() +
                " | Authorities: " + auth.getAuthorities();

        return ResponseEntity.ok(
                ApiResponse.success(debug)
        );
    }

    /*
     * USER CREATE TRIP
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ApiResponse<Trip>> createTrip(@RequestBody Trip trip) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        // enforce tenant ownership
        trip.setTenantId(user.getTenantId());

        // enforce initial lifecycle state
        trip.setStatus(TripStatus.PENDING);

        Trip saved = tripService.create(trip);

        return ResponseEntity.ok(
                ApiResponse.success(saved)
        );
    }

    /*
     * ADMIN APPROVE TRIP
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<Trip>> approveTrip(@PathVariable Long id) {

        Trip trip = tripService.approveTrip(id);

        return ResponseEntity.ok(
                ApiResponse.success(trip)
        );
    }

    /*
     * ADMIN REJECT TRIP
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<Trip>> rejectTrip(@PathVariable Long id) {

        Trip trip = tripService.rejectTrip(id);

        return ResponseEntity.ok(
                ApiResponse.success(trip)
        );
    }

    /*
     * USER START TRIP
     */
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/start")
    public ResponseEntity<ApiResponse<Trip>> startTrip(@PathVariable Long id) {

        Trip trip = tripService.startTrip(id);

        return ResponseEntity.ok(
                ApiResponse.success(trip)
        );
    }

    /*
     * USER COMPLETE TRIP
     */
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<Trip>> completeTrip(@PathVariable Long id) {

        Trip trip = tripService.completeTrip(id);

        return ResponseEntity.ok(
                ApiResponse.success(trip)
        );
    }

    /*
     * USER EMERGENCY
     */
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/emergency")
    public ResponseEntity<ApiResponse<Trip>> emergencyTrip(@PathVariable Long id) {

        Trip trip = tripService.emergencyTrip(id);

        return ResponseEntity.ok(
                ApiResponse.success(trip)
        );
    }
}