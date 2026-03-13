package com.journeymanager.journeybackend.repository;

import com.journeymanager.journeybackend.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import com.journeymanager.journeybackend.trip.domain.TripStatus;
import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    long countByTenantId(Long tenantId);

    long countByTenantIdAndStatus(Long tenantId, TripStatus status);
    List<Trip> findByTenantId(Long tenantId);

    Optional<Trip> findByIdAndTenantId(Long id, Long tenantId);
}