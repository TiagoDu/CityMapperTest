package com.tiago.citymapperproject.features.stoppoints.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.tiago.citymapperproject.features.stoppoints.model.StopPoint;
import io.reactivex.Flowable;
import java.util.List;

@Dao public interface StopPointsDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE) long add(StopPoint stopPoint);

  @Query("SELECT * FROM StopPoint") Flowable<List<StopPoint>> getStopPoints();
}
