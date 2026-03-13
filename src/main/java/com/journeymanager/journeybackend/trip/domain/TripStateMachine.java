package com.journeymanager.journeybackend.trip.domain;

public class TripStateMachine {

    public static void validateApprove(Trip trip) {

        if (trip.getStatus() != TripStatus.PENDING) {
            throw new IllegalStateException("Only PENDING trips can be approved");
        }
    }

    public static void validateReject(Trip trip) {

        if (trip.getStatus() != TripStatus.PENDING) {
            throw new IllegalStateException("Only PENDING trips can be rejected");
        }
    }

    public static void validateStart(Trip trip) {

        if (trip.getStatus() != TripStatus.APPROVED) {
            throw new IllegalStateException("Trip must be APPROVED before starting");
        }
    }

    public static void validateComplete(Trip trip) {

        if (trip.getStatus() != TripStatus.IN_PROGRESS) {
            throw new IllegalStateException("Trip must be IN_PROGRESS before completion");
        }
    }

    public static void validateEmergency(Trip trip) {

        if (trip.getStatus() != TripStatus.IN_PROGRESS) {
            throw new IllegalStateException("Emergency only allowed during active trip");
        }
    }
}
