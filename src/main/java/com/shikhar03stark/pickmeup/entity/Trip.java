package com.shikhar03stark.pickmeup.entity;

import com.shikhar03stark.util.IdGenerator;
import com.shikhar03stark.util.OTPGenerator;

public class Trip {
    private final int id;
    private final String otp;
    private TripState tripState;
    private Location startPoint;
    private Location dropPoint;
    private VehicleType vehicleType;
    private Driver drivenBy;
    private Rider tripFor;

    public Trip(Location startPoint, Location dropPoint, VehicleType vehicleType, Rider tripFor) {
        this.id = IdGenerator.generateId();
        this.otp = OTPGenerator.generateOTP();
        this.tripState = TripState.REQUESTED;
        this.startPoint = startPoint;
        this.dropPoint = dropPoint;
        this.vehicleType = vehicleType;
        this.tripFor = tripFor;
    }

    public int getId() {
        return id;
    }

    public String getOtp() {
        return otp;
    }

    public Location getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Location startPoint) {
        this.startPoint = startPoint;
    }

    public Location getDropPoint() {
        return dropPoint;
    }

    public void setDropPoint(Location dropPoint) {
        this.dropPoint = dropPoint;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Driver getDrivenBy() {
        return drivenBy;
    }

    public void setDrivenBy(Driver drivenBy) {
        this.drivenBy = drivenBy;
    }

    public Rider getTripFor() {
        return tripFor;
    }

    public void setTripFor(Rider tripFor) {
        this.tripFor = tripFor;
    }

    public TripState getTripState() {
        return tripState;
    }

    public void setTripState(TripState tripState) {
        this.tripState = tripState;
    }

    
}
