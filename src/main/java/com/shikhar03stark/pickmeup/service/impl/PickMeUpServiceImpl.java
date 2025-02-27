package com.shikhar03stark.pickmeup.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.shikhar03stark.pickmeup.entity.Driver;
import com.shikhar03stark.pickmeup.entity.Location;
import com.shikhar03stark.pickmeup.entity.Rider;
import com.shikhar03stark.pickmeup.entity.Trip;
import com.shikhar03stark.pickmeup.entity.TripState;
import com.shikhar03stark.pickmeup.entity.VehicleType;
import com.shikhar03stark.pickmeup.service.PickMeUpService;
import com.shikhar03stark.pickmeup.service.TripMatcherService;

public class PickMeUpServiceImpl implements PickMeUpService {

    private final Map<Integer, Rider> ridersMap = new HashMap<>();
    private final Map<Integer, Driver> driversMap = new HashMap<>();
    private final TripMatcherService _tripMatcherService;

    public PickMeUpServiceImpl(TripMatcherService tripMatcherService) {
        this._tripMatcherService = tripMatcherService;
    }

    @Override
    public void addDriver(Driver driver) {
        driversMap.put(driver.getId(), driver);
    }

    @Override
    public void addRider(Rider rider) {
        ridersMap.put(rider.getId(), rider);
    }

    @Override
    public Trip requestTrip(Rider rider, Location startPoint, Location dropPoint, VehicleType vehicleType) {
        
        final Trip requestedTrip = new Trip(startPoint, dropPoint, vehicleType, rider);
        _tripMatcherService.acceptTripRequest(requestedTrip);
        return requestedTrip;
    }

    @Override
    public List<Trip> availableTrips(Driver driver) {
        return _tripMatcherService.availableTrips(driver.getVehicleType());
    }

    @Override
    public void serveTrip(Driver driver, Trip trip) {
        
        if (trip.getTripState() != TripState.REQUESTED) {
            throw new RuntimeException("Can't serve a trip if in REQUESTED state");
        }

        if (driver.isBusy()) {
            throw new RuntimeException("Driver busy in another ride");
        }

        driver.setBusy(true);
        trip.setDrivenBy(driver);
        trip.setTripState(TripState.DRIVER_ASSIGNED);
        _tripMatcherService.removeTripRequest(trip);
    }

    @Override
    public void startTrip(Driver driver, Trip trip, String otp) {
        if (trip.getTripState() != TripState.DRIVER_ASSIGNED) {
            throw new RuntimeException("Can't start a trip if driver is not assigned");
        }

        if (driver.getId() != trip.getDrivenBy().getId()) {
            throw new RuntimeException("Assigned driver is different than starting driver");
        }

        if (!trip.getOtp().equals(otp)) {
            throw new RuntimeException("Shared otp does not match trip OTP");
        }

        trip.setTripState(TripState.ENROUTE);
    }

    @Override
    public void completeTrip(Driver driver, Trip trip) {
        if (trip.getTripState() != TripState.ENROUTE) {
            throw new RuntimeException("Trip has not started yet");
        }

        if (trip.getDrivenBy().getId() != driver.getId()) {
            throw new RuntimeException("Driver who started the ride is not same as completing the ride");
        }

        trip.setTripState(TripState.COMPLETED);
        driver.setBusy(false);

        // add in rider's history
        final Rider rider = trip.getTripFor();
        rider.addTripToHistory(trip);
    }

    @Override
    public void cancelTrip(Driver driver, Trip trip) {
        // Driver can only can if it is the assigned driver and trip is in ASSIGNED state
        if (trip.getTripState() != TripState.DRIVER_ASSIGNED) {
            throw new RuntimeException("Driver can't cancel a trip when not in DRIVER_ASSIGNED state");
        }

        if (Objects.isNull(trip.getDrivenBy()) || driver.getId() != trip.getDrivenBy().getId()) {
            throw new RuntimeException("Trip has no assigned driver or driver does not match assigned driver");
        }

        trip.setTripState(TripState.REQUESTED);
        driver.setBusy(false);
        trip.setDrivenBy(null);

        // add in TripMatcher
        _tripMatcherService.acceptTripRequest(trip);
    }

    @Override
    public void cancelTrip(Rider rider, Trip trip) {
        // Rider can only cancel if REQUESTED and DRIVER_ASSIGNED state
        if (trip.getTripState() != TripState.REQUESTED && trip.getTripState() != TripState.DRIVER_ASSIGNED) {
            throw new RuntimeException("Trip is neither in REQUETSED or DRIVER_ASSIGNED state");
        }

        if (trip.getTripFor().getId() != rider.getId()) {
            throw new RuntimeException("Trip rider is different from the provided rider");
        }

        // if in DRIVER_ASSIGNED state
        // free the driver
        if (trip.getTripState() == TripState.DRIVER_ASSIGNED) {
            trip.getDrivenBy().setBusy(false);
            trip.setDrivenBy(null);
        }

        trip.setTripState(TripState.CANCELED);
    }

    @Override
    public List<Trip> viewTrips(Rider rider) {
        return rider.getTrips();
    }

}
