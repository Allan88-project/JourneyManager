package com.journeymanager.journeybackend.trip.replay;

import com.journeymanager.journeybackend.trip.location.domain.TripLocation;
import com.journeymanager.journeybackend.trip.location.repository.TripLocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripReplayService {

    private final TripLocationRepository repository;

    public TripReplayService(TripLocationRepository repository) {
        this.repository = repository;
    }

    public List<TripReplayPoint> getReplay(Long tripId) {

        List<TripLocation> locations =
                repository.findByTripIdOrderByTimestampAsc(tripId);

        return locations.stream()
                .map(l -> new TripReplayPoint(
                        l.getLatitude(),
                        l.getLongitude(),
                        l.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}
