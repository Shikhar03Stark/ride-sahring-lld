package com.shikhar03stark.pickmeup.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.shikhar03stark.pickmeup.entity.Trip;
import com.shikhar03stark.pickmeup.entity.VehicleType;
import com.shikhar03stark.pickmeup.service.TripMatcherService;

public class TripMatcherServiceImpl implements TripMatcherService {

    private final Map<VehicleType, Set<Trip>> requestedTripMap = new HashMap<>();

    @Override
    public void acceptTripRequest(Trip trip) {
        
        if (!requestedTripMap.containsKey(trip.getVehicleType())) {
            requestedTripMap.put(trip.getVehicleType(), new HashSet<>());
        }
        requestedTripMap.get(trip.getVehicleType()).add(trip);
    }
    
    @Override
    public void removeTripRequest(Trip trip) {
        
        if (requestedTripMap.containsKey(trip.getVehicleType())) {
            requestedTripMap.get(trip.getVehicleType()).remove(trip);
        }
    }

    @Override
    public List<Trip> availableTrips(VehicleType vehicleType) {
        
        if (requestedTripMap.containsKey(vehicleType)) {
            return requestedTripMap.get(vehicleType).stream().collect(Collectors.toList());
        } 
        else {
            return new ArrayList<>();
        }
    }

}
