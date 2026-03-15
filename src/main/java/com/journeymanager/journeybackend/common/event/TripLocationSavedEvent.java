package com.journeymanager.journeybackend.common.event;

import java.time.Instant;

public class TripLocationSavedEvent {

    private final Long tripId;
    private final double latitude;
    private final double longitude;
    private final Instant timestamp;

    public TripLocationSavedEvent(
            Long tripId,
            double latitude,
            double longitude,
            Instant timestamp
    ) {
        this.tripId = tripId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public Long getTripId() {
        return tripId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
