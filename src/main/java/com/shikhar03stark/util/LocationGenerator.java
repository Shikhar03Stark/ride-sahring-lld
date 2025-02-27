package com.shikhar03stark.util;

import com.shikhar03stark.pickmeup.entity.Location;

public class LocationGenerator {

    public static Location generateLocation() {
        final double latitude = Math.random() * 180 - 90;
        final double longitude = Math.random() * 360 - 180;
        return new Location(latitude, longitude);
    }
}
