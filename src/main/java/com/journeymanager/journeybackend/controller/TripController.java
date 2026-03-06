package com.journeymanager.journeybackend.controller;

import com.journeymanager.journeybackend.model.trip.Trip;
import com.journeymanager.journeybackend.model.trip.TripStatus;
import com.journeymanager.journeybackend.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService service;

    public TripController(TripService service) {
        this.service = service;
    }

    @GetMapping
    public List<Trip> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Trip create(@RequestBody Trip trip) {
        return service.create(trip);
    }

    // ADMIN approve/reject
    @PutMapping("/{id}/status")
    public Trip updateStatus(
            @PathVariable Long id,
            @RequestParam TripStatus status
    ) {
        return service.updateStatus(id, status);
    }

    // USER start journey
    @PutMapping("/{id}/start")
    public Trip start(@PathVariable Long id) {
        return service.startJourney(id);
    }

    // USER complete journey
    @PutMapping("/{id}/complete")
    public Trip complete(@PathVariable Long id) {
        return service.completeJourney(id);
    }

    // USER emergency trigger
    @PutMapping("/{id}/emergency")
    public Trip emergency(@PathVariable Long id) {
        return service.triggerEmergency(id);
    }

}