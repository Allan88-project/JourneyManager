package com.journeymanager.journeybackend.analytics.dto;

public class AdminAnalyticsResponse {

    private long totalTrips;
    private long pending;
    private long approved;
    private long rejected;
    private long inProgress;
    private long completed;
    private long emergency;

    public AdminAnalyticsResponse(
            long totalTrips,
            long pending,
            long approved,
            long rejected,
            long inProgress,
            long completed,
            long emergency
    ) {
        this.totalTrips = totalTrips;
        this.pending = pending;
        this.approved = approved;
        this.rejected = rejected;
        this.inProgress = inProgress;
        this.completed = completed;
        this.emergency = emergency;
    }

    public long getTotalTrips() {
        return totalTrips;
    }

    public long getPending() {
        return pending;
    }

    public long getApproved() {
        return approved;
    }

    public long getRejected() {
        return rejected;
    }

    public long getInProgress() {
        return inProgress;
    }

    public long getCompleted() {
        return completed;
    }

    public long getEmergency() {
        return emergency;
    }
}
