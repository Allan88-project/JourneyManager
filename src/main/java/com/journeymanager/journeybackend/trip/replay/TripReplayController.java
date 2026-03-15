package com.journeymanager.journeybackend.trip.replay;

import com.journeymanager.journeybackend.common.feature.RequiresFeature;
import com.journeymanager.journeybackend.tenant.subscription.Feature;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/trips")
public class TripReplayController {

    private final TripReplayService replayService;

    public TripReplayController(TripReplayService replayService) {
        this.replayService = replayService;
    }

    @RequiresFeature(Feature.TRIP_REPLAY)
    @GetMapping("/{tripId}/replay")
    public List<TripReplayPoint> replayTrip(@PathVariable Long tripId) {

        return replayService.getReplay(tripId);
    }
}