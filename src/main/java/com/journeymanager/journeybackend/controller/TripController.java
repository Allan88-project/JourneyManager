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
        System.out.println("TripController CREATE called");
        return service.create(trip);
    }

    @PutMapping("/{id}/status")
    public Trip updateStatus(
            @PathVariable Long id,
            @RequestParam TripStatus status
    ) {
        return service.updateStatus(id, status);
    }
}