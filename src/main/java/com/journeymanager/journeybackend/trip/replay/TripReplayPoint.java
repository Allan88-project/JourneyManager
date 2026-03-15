package com.journeymanager.journeybackend.trip.replay;

import java.time.LocalDateTime;

public class TripReplayPoint {

    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;

    public TripReplayPoint(double latitude, double longitude, LocalDateTime timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
