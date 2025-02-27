package com.shikhar03stark.pickmeup.service;

import java.util.List;

import com.shikhar03stark.pickmeup.entity.Driver;
import com.shikhar03stark.pickmeup.entity.Location;
import com.shikhar03stark.pickmeup.entity.Rider;
import com.shikhar03stark.pickmeup.entity.Trip;
import com.shikhar03stark.pickmeup.entity.VehicleType;

public interface PickMeUpService {
    
    // Register users
    void addDriver(Driver driver);
    void addRider(Rider rider);

    // Request a trip
    Trip requestTrip(Rider rider, Location startPoint, Location dropPoint, VehicleType vehicleType);

    // view available trips to driver
    List<Trip> availableTrips(Driver driver);

    // Trip management
    void serveTrip(Driver driver, Trip trip);
    void startTrip(Driver driver, Trip trip, String otp);
    void completeTrip(Driver driver, Trip trip);
    void cancelTrip(Driver driver, Trip trip);
    void cancelTrip(Rider rider, Trip trip);

    // trip summary
    List<Trip> viewTrips(Rider rider);
}
