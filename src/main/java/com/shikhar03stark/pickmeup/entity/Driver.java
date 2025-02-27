package com.shikhar03stark.pickmeup.entity;

public class Driver extends User {

    private final String vehicleNumber;
    private final VehicleType vehicleType;
    private boolean isOnline; // active on the app
    private boolean isBusy; // serving a ride

    public Driver(String name, String gender, String vehicleNumber, VehicleType vehicleType) {
        super(name, gender);
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.isOnline = true;
        this.isBusy = false;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    
    
}
