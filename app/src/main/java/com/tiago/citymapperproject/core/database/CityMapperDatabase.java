package com.tiago.citymapperproject.core.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.tiago.citymapperproject.features.stoppoints.database.StopPointsDao;
import com.tiago.citymapperproject.features.stoppoints.model.StopPoint;

@Database(entities = { StopPoint.class }, version = 1, exportSchema = false) public abstract class CityMapperDatabase
    extends RoomDatabase {

  private static volatile CityMapperDatabase INSTANCE;

  public abstract StopPointsDao stopPointsDao();
}