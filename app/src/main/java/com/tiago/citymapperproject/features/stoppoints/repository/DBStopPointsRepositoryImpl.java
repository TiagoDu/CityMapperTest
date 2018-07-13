package com.tiago.citymapperproject.features.stoppoints.repository;

import com.tiago.citymapperproject.core.MySchedulers;
import com.tiago.citymapperproject.features.stoppoints.database.StopPointsDao;
import com.tiago.citymapperproject.features.stoppoints.model.StopPoint;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton public class DBStopPointsRepositoryImpl implements DBStopPointsRepository {

  private final StopPointsDao stopPointsDao;
  private final MySchedulers schedulers;

  @Inject public DBStopPointsRepositoryImpl(StopPointsDao stopPointsDao, MySchedulers schedulers) {
    this.stopPointsDao = stopPointsDao;
    this.schedulers = schedulers;
  }

  @Override public Single<List<StopPoint>> add(List<StopPoint> stopPoints) {
    return Single.fromCallable(() -> {
      for (int i = 0; i < stopPoints.size(); i++) {
        stopPointsDao.add(stopPoints.get(i));
      }

      return stopPoints;
    });
  }

  public Flowable<List<StopPoint>> getStopPoints(Double lon, Double lat) {
    return stopPointsDao.getStopPoints().subscribeOn(schedulers.io()).observeOn(schedulers.ui());
  }
}
