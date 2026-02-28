package com.journeymanager.journeybackend.repository;

import com.journeymanager.journeybackend.model.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByTenantId(Long tenantId);

    List<Trip> findByUserId(Long userId);
}

