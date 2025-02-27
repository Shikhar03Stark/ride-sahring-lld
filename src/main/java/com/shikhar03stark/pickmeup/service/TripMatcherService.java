package com.shikhar03stark.pickmeup.service;

import java.util.List;

import com.shikhar03stark.pickmeup.entity.Trip;
import com.shikhar03stark.pickmeup.entity.VehicleType;

public interface TripMatcherService {

    void acceptTripRequest(Trip trip);
    void removeTripRequest(Trip trip);
    List<Trip> availableTrips(VehicleType vehicleType);
}
