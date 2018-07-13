package com.tiago.citymapperproject.features.stoppoints.repository;

import com.tiago.citymapperproject.features.stoppoints.model.StopPoint;
import io.reactivex.Single;
import java.util.List;

public interface ApiStopPointsRepository {

  Single<List<StopPoint>> getStopPoints(Double lon, Double lat);
}
