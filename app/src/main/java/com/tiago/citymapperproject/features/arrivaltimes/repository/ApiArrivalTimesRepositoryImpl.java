package com.tiago.citymapperproject.features.arrivaltimes.repository;

import com.tiago.citymapperproject.core.MySchedulers;
import com.tiago.citymapperproject.features.arrivaltimes.api.ArrivalTimesApi;
import com.tiago.citymapperproject.features.arrivaltimes.model.ArrivalTime;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton public class ApiArrivalTimesRepositoryImpl implements ApiArrivalTimesRepository {

  private final ArrivalTimesApi arrivalTimesApi;
  private final MySchedulers schedulers;

  @Inject public ApiArrivalTimesRepositoryImpl(ArrivalTimesApi arrivalTimesApi,
      MySchedulers schedulers) {
    this.arrivalTimesApi = arrivalTimesApi;
    this.schedulers = schedulers;
  }

  @Override public Single<List<ArrivalTime>> getArrivalTimes(String naptanId) {
    return arrivalTimesApi.getArrivalTimes(naptanId)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui());
  }
}
