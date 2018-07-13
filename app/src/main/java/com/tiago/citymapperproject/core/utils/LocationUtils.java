package com.tiago.citymapperproject.core.utils;

import android.location.Location;

public class LocationUtils {

  public static final float getDistance(double lon1, double lat1, double lon2, double lat2) {
    Location firstLocation = new Location("");
    firstLocation.setLongitude(lon1);
    firstLocation.setLatitude(lat1);
    Location secondLocation = new Location("");
    secondLocation.setLongitude(lon2);
    secondLocation.setLatitude(lat2);
    return firstLocation.distanceTo(secondLocation);
  }
}
