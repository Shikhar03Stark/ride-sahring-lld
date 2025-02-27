package com.shikhar03stark;

import com.shikhar03stark.pickmeup.entity.Driver;
import com.shikhar03stark.pickmeup.entity.Rider;
import com.shikhar03stark.pickmeup.entity.Trip;
import com.shikhar03stark.pickmeup.entity.VehicleType;
import com.shikhar03stark.pickmeup.service.PickMeUpService;
import com.shikhar03stark.pickmeup.service.TripMatcherService;
import com.shikhar03stark.pickmeup.service.impl.PickMeUpServiceImpl;
import com.shikhar03stark.pickmeup.service.impl.TripMatcherServiceImpl;
import com.shikhar03stark.util.LocationGenerator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        final TripMatcherService tripMatcherService = new TripMatcherServiceImpl();
        final PickMeUpService pickMeUpService = new PickMeUpServiceImpl(tripMatcherService);

        final Driver d1 = new Driver("Joe", "Male", "UP16", VehicleType.MINI);
        final Driver d2 = new Driver("Rogan", "Male", "UP14", VehicleType.MINI);
        final Driver d3 = new Driver("Tom", "Male", "KA05", VehicleType.AUTO);

        final Rider r1 = new Rider("Sam", "Male");
        final Rider r2 = new Rider("Altman", "Male");
        final Rider r3 = new Rider("Rob", "Male");

        pickMeUpService.addDriver(d1);
        pickMeUpService.addDriver(d2);
        pickMeUpService.addDriver(d3);

        pickMeUpService.addRider(r1);
        pickMeUpService.addRider(r2);
        pickMeUpService.addRider(r3);

        final Trip t1 = pickMeUpService.requestTrip(
            r1,
            LocationGenerator.generateLocation(),
            LocationGenerator.generateLocation(),
            VehicleType.MINI
        );

        final Trip t2 = pickMeUpService.requestTrip(
            r2,
            LocationGenerator.generateLocation(),
            LocationGenerator.generateLocation(),
            VehicleType.MINI
        );

        final Trip t3 = pickMeUpService.requestTrip(
            r3,
            LocationGenerator.generateLocation(),
            LocationGenerator.generateLocation(),
            VehicleType.AUTO
        );

        showAvailableTrips(pickMeUpService, d1);
        showAvailableTrips(pickMeUpService, d3);

        pickMeUpService.serveTrip(d1, t1);
        pickMeUpService.serveTrip(d3, t3);

        showAvailableTrips(pickMeUpService, d1);
        showAvailableTrips(pickMeUpService, d3);

        // d1 completes
        pickMeUpService.startTrip(d1, t1, t1.getOtp());
        pickMeUpService.completeTrip(d1, t1);

        // d2 cancels before starting
        pickMeUpService.cancelTrip(d3, t3);

        // shoudl have t3 available now
        showAvailableTrips(pickMeUpService, d3);

        // d2 starts
        pickMeUpService.serveTrip(d2, t2);
        // rider cancels
        pickMeUpService.cancelTrip(r2, t2);

        // should be empty now
        showAvailableTrips(pickMeUpService, d1);
    }

    private static void showAvailableTrips(PickMeUpService pickMeUpService, Driver driver) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Available trips for ").append(driver.getName()).append(": ");
        pickMeUpService.availableTrips(driver).forEach(trip -> sb.append(trip.getId()).append(", "));
        System.out.println(sb.toString());
    }
}
