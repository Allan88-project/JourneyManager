package com.journeymanager.journeybackend.analytics.controller;

import com.journeymanager.journeybackend.analytics.dto.AdminAnalyticsResponse;
import com.journeymanager.journeybackend.analytics.service.AdminAnalyticsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminAnalyticsController {

    private final AdminAnalyticsService analyticsService;

    public AdminAnalyticsController(AdminAnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/analytics")
    public AdminAnalyticsResponse getAnalytics() {
        return analyticsService.getAnalytics();
    }
}
