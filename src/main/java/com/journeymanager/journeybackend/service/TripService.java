package com.journeymanager.journeybackend.service;

import com.journeymanager.journeybackend.model.trip.Trip;
import com.journeymanager.journeybackend.model.trip.TripStatus;
import com.journeymanager.journeybackend.repository.TripRepository;
import com.journeymanager.journeybackend.tenant.TenantContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    private final TripRepository repository;

    public TripService(TripRepository repository) {
        this.repository = repository;
    }

    public List<Trip> findAll() {
        Long tenantId = TenantContext.getTenantId();
        return repository.findByTenantId(tenantId);
    }

    public Trip create(Trip trip) {
        return repository.save(trip);
    }

    public Trip updateStatus(Long id, TripStatus newStatus) {

        Long tenantId = TenantContext.getTenantId();

        Trip existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        // Ensure tenant ownership
        if (!existing.getTenantId().equals(tenantId)) {
            throw new RuntimeException("Unauthorized access to this trip");
        }

        // Enforce lifecycle rule
        if (existing.getStatus() != TripStatus.PENDING) {
            throw new RuntimeException("Only PENDING trips can be updated");
        }

        existing.setStatus(newStatus);

        return repository.save(existing);
    }
}