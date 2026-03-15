package com.journeymanager.journeybackend.analytics.service;

import com.journeymanager.journeybackend.analytics.dto.AdminAnalyticsResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public AnalyticsBroadcaster(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    public void broadcastTripLocation(Long tripId, Object payload) {

        messagingTemplate.convertAndSend(
                "/topic/trip/" + tripId,
                payload
        );

    }
    public void broadcast(AdminAnalyticsResponse analytics) {
        messagingTemplate.convertAndSend(
                "/topic/admin/analytics",
                analytics
        );
    }

}

