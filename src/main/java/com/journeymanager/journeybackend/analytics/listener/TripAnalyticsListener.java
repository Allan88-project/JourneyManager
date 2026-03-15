package com.journeymanager.journeybackend.analytics.listener;

import com.journeymanager.journeybackend.analytics.dto.AdminAnalyticsResponse;
import com.journeymanager.journeybackend.analytics.service.AdminAnalyticsService;
import com.journeymanager.journeybackend.analytics.service.AnalyticsBroadcaster;
import com.journeymanager.journeybackend.trip.domain.event.TripCreatedEvent;
import com.journeymanager.journeybackend.trip.domain.event.TripStartedEvent;
import com.journeymanager.journeybackend.trip.domain.event.TripCompletedEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TripAnalyticsListener {

    private final AdminAnalyticsService analyticsService;
    private final AnalyticsBroadcaster broadcaster;

    public TripAnalyticsListener(
            AdminAnalyticsService analyticsService,
            AnalyticsBroadcaster broadcaster
    ) {
        this.analyticsService = analyticsService;
        this.broadcaster = broadcaster;
    }

    @EventListener
    public void handleTripCreated(TripCreatedEvent event) {

        analyticsService.refreshCache();

        AdminAnalyticsResponse analytics =
                analyticsService.getAnalytics();

        broadcaster.broadcast(analytics);
    }

    @EventListener
    public void handleTripStarted(TripStartedEvent event) {

        analyticsService.refreshCache();

        AdminAnalyticsResponse analytics =
                analyticsService.getAnalytics();

        broadcaster.broadcast(analytics);
    }

    @EventListener
    public void handleTripCompleted(TripCompletedEvent event) {

        analyticsService.refreshCache();

        AdminAnalyticsResponse analytics =
                analyticsService.getAnalytics();

        broadcaster.broadcast(analytics);
    }

}
