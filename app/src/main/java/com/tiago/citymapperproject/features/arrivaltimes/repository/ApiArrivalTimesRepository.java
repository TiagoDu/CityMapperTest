package com.tiago.citymapperproject.features.arrivaltimes.repository;

import com.tiago.citymapperproject.features.arrivaltimes.model.ArrivalTime;
import io.reactivex.Single;
import java.util.List;

public interface ApiArrivalTimesRepository {

  Single<List<ArrivalTime>> getArrivalTimes(String naptanId);
}
