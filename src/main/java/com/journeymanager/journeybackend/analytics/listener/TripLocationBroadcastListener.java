package com.journeymanager.journeybackend.analytics.listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.journeymanager.journeybackend.analytics.service.AnalyticsBroadcaster;
import com.journeymanager.journeybackend.common.event.TripLocationSavedEvent;
import com.journeymanager.journeybackend.trip.location.dto.LocationBroadcast;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TripLocationBroadcastListener {
    private static final Logger log =
            LoggerFactory.getLogger(TripLocationBroadcastListener.class);
    private final AnalyticsBroadcaster analyticsBroadcaster;

    public TripLocationBroadcastListener(AnalyticsBroadcaster analyticsBroadcaster) {
        this.analyticsBroadcaster = analyticsBroadcaster;
    }

    @EventListener
    public void onTripLocationSaved(TripLocationSavedEvent event) {
        log.info(
                "[LiveTracking] Trip {} broadcast -> lat={} lon={}",
                event.getTripId(),
                event.getLatitude(),
                event.getLongitude()
        );
        LocationBroadcast broadcast = new LocationBroadcast(
                event.getTripId(),
                event.getLatitude(),
                event.getLongitude(),
                0.0,
                0.0
        );

        analyticsBroadcaster.broadcastTripLocation(
                event.getTripId(),
                broadcast
        );
    }
}