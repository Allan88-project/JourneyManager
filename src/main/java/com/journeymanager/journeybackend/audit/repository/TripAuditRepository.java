package com.journeymanager.journeybackend.audit.repository;

import com.journeymanager.journeybackend.audit.domain.TripAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripAuditRepository extends JpaRepository<TripAudit, Long> {

    List<TripAudit> findByTripIdOrderByTimestampAsc(Long tripId);

}
