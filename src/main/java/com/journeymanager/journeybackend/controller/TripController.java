package com.journeymanager.journeybackend.controller;

import com.journeymanager.journeybackend.model.trip.Trip;
import com.journeymanager.journeybackend.model.trip.TripStatus;
import com.journeymanager.journeybackend.security.CustomUserDetails;
import com.journeymanager.journeybackend.service.TripService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
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

    // LIST trips
    @GetMapping
    public ResponseEntity<List<Trip>> getAll() {
        return ResponseEntity.ok(tripService.findAll());
    }
    @GetMapping("/debug")
    public ResponseEntity<String> debugAuth() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.ok("Authentication is NULL");
        }

        return ResponseEntity.ok(
                "Principal: " + auth.getPrincipal() +
                        " | Authorities: " + auth.getAuthorities()
        );
    }
    // USER create trip
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        trip.setTenantId(user.getTenantId());
        trip.setStatus(TripStatus.PENDING);

        Trip saved = tripService.create(trip);

        return ResponseEntity.ok(saved);
    }

    // ADMIN approve / reject
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Trip> updateStatus(
            @PathVariable Long id,
            @RequestParam TripStatus status) {
        return ResponseEntity.ok(tripService.updateStatus(id, status));
    }

    // USER start journey
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/start")
    public ResponseEntity<Trip> start(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.startJourney(id));
    }

    // USER complete journey
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/complete")
    public ResponseEntity<Trip> complete(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.completeJourney(id));
    }

    // USER emergency trigger
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/emergency")
    public ResponseEntity<Trip> emergency(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.triggerEmergency(id));
    }

}