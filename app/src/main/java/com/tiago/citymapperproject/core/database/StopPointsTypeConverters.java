package com.tiago.citymapperproject.core.database;

import android.arch.persistence.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tiago.citymapperproject.features.stoppoints.model.StopPoint;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class StopPointsTypeConverters {

  private static final Gson gson = new Gson();

  @TypeConverter public static List<StopPoint> stringToStopPointList(String data) {
    if (data == null) {
      return Collections.emptyList();
    }

    Type listType = new TypeToken<List<StopPoint>>() {
    }.getType();

    return gson.fromJson(data, listType);
  }

  @TypeConverter public static String stopPointListToString(List<StopPoint> stopPoints) {
    return gson.toJson(stopPoints);
  }
}