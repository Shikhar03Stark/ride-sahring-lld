package com.shikhar03stark.pickmeup.entity;

import java.util.ArrayList;
import java.util.List;

public class Rider extends User {

    private final List<Trip> trips = new ArrayList<>();

    public Rider(String name, String gender) {
        super(name, gender);
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void addTripToHistory(Trip trip) {
        trips.add(trip);
    }
    
}
