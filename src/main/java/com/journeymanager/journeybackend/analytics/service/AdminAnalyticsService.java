package com.journeymanager.journeybackend.analytics.service;

import com.journeymanager.journeybackend.analytics.dto.AdminAnalyticsResponse;
import com.journeymanager.journeybackend.trip.domain.TripStatus;
import com.journeymanager.journeybackend.repository.TripRepository;
import com.journeymanager.journeybackend.security.CustomUserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AdminAnalyticsService {

    private final TripRepository tripRepository;

    public AdminAnalyticsService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    private Long getCurrentTenantId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        return user.getTenantId();
    }

    public AdminAnalyticsResponse getAnalytics() {

        Long tenantId = getCurrentTenantId();

        long total = tripRepository.countByTenantId(tenantId);

        long pending = tripRepository.countByTenantIdAndStatus(
                tenantId, TripStatus.PENDING);

        long approved = tripRepository.countByTenantIdAndStatus(
                tenantId, TripStatus.APPROVED);

        long rejected = tripRepository.countByTenantIdAndStatus(
                tenantId, TripStatus.REJECTED);

        long inProgress = tripRepository.countByTenantIdAndStatus(
                tenantId, TripStatus.IN_PROGRESS);

        long completed = tripRepository.countByTenantIdAndStatus(
                tenantId, TripStatus.COMPLETED);

        long emergency = tripRepository.countByTenantIdAndStatus(
                tenantId, TripStatus.EMERGENCY);

        return new AdminAnalyticsResponse(
                total,
                pending,
                approved,
                rejected,
                inProgress,
                completed,
                emergency
        );
    }
}