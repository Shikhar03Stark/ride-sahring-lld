# Pick Me Up (Uber-like) LLD

## Requirements
1. Ability to signup users (drivers and riders)
2. Riders can request for a ride
3. Drivers can accept a ride request
4. Drivers can start the ride, after picking up user
5. Drivers can end the ride.
6. Riders can cancel the ride before pickup.
7. Riders can view the completed trip details

## Clarifications

### 1. Ability to signup users

We can signup a driver and a rider

### 2. Riders can request for a ride

A ride request contains vehicleType, startPoint, dropPoint.
After a successful request, the rider will get a unique OTP.

### 4. Drivers can start the ride, after picking up the user

The drivers can only start a trip when the OTP shared by the rider matches.

### 5. Drivers can end the ride.

The riders can not cancel the ride in between.

### 6. Riders can cancel the ride before pickup

The riders can only cancel a ride request, after trip has started rider can not cancel

### 7. Riders can view the completed trip details

The trip details include, Ride startPoint, dropPoint, Driver Name, vehicle type

## Approach

The flow of the entire application

1. Driver D sign up with vehicleNum and vehicleType
2. Rider R sign up
3. R requests for a ride from source to destination, for vehicleType Mini and gets a OTP
4. D can view ride requests
5. D can pick a ride request to serve
6. D arrives at source, R shares the OTP
7. D starts the ride with OTP
8. D completes the trip
9. R views the ride details after completion

### Entities

```
Location {
    latitude,
    longitude,
}

TripState {
    REQUESTED, // rider requested for a trip
    DRIVER_ACCEPTED, // driver wants to serve this trip
    ENROUTE, // driver picked up user using OTP
    CANCELLED,
    COMPLETED
}

VehicleType {
    AUTO,
    MINI,
    SEDAN,
    PREMIUM,
}

User {
    id,
    name,
    gender,
}

Rider: User {
    completedTrips,
}

Driver: User {
    vehicleNum,
    vehivleType,
    completedTrips
}

Trip {
    id,
    otp,
    startPoint,
    dropPoint,
    vehicleType,
    tripState,
    drivenBy,
    tripFor,
}
```

### Service

```
PickMeUpService {
    addRider(Rider): void
    addDriver(Driver): void
    requestTrip(Rider, startPoint, endPoint, vehicleType): Trip
    availableTrips(Driver): List<Trip>
    serveTrip(Driver, Trip): void
    startTrip(Driver, Trip, sharedOTP): void
    completeTrip(Driver, Trip): void
    cancelTrip(Rider): void
    cancelTrip(Driver): void
    viewCompletedTrips(Rider): List<Trip>
}
```

### Design Principles

1. PickMeService is a singleton
2. Trip can be managed by a state machine
3. availableTrips can implement strategy pattern based on Driver's preferences and proximity