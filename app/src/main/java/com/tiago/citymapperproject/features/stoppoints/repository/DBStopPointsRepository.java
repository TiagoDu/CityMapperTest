package com.tiago.citymapperproject.features.stoppoints.repository;

import com.tiago.citymapperproject.features.stoppoints.model.StopPoint;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;

public interface DBStopPointsRepository {

  Single<List<StopPoint>> add(List<StopPoint> stopPoints);

  Flowable<List<StopPoint>> getStopPoints(Double lon, Double lat);
}