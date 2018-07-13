package com.tiago.citymapperproject.features.stoppoints.repository;

import com.tiago.citymapperproject.core.MySchedulers;
import com.tiago.citymapperproject.features.stoppoints.api.StopPointsApi;
import com.tiago.citymapperproject.features.stoppoints.model.StopPoint;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton public class ApiStopPointsRepositoryImpl implements ApiStopPointsRepository {

  private final StopPointsApi stopPointsApi;
  private final MySchedulers schedulers;

  @Inject public ApiStopPointsRepositoryImpl(StopPointsApi stopPointsApi, MySchedulers schedulers) {
    this.stopPointsApi = stopPointsApi;
    this.schedulers = schedulers;
  }

  @Override public Single<List<StopPoint>> getStopPoints(Double lon, Double lat) {
    return stopPointsApi.nearbyStopPoint(lon, lat, StopPointsApi.STOP_TYPES, StopPointsApi.RADIUS)
        .map(stopPointResponse -> stopPointResponse.getStopPoints())
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui());
  }
}
