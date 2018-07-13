package com.tiago.citymapperproject.core.constants;

import android.location.Location;

public class LondonConstants {

  public static final double LON = -0.089095;
  public static final double LAT = 51.513395;

  private static Location LONDON_LOCATION;

  public static Location getLondonLocation() {
    if (LONDON_LOCATION == null) {
      LONDON_LOCATION = new Location("");
      LONDON_LOCATION.setLongitude(LON);
      LONDON_LOCATION.setLatitude(LAT);
    }

    return LONDON_LOCATION;
  }
}
