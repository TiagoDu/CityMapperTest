package com.tiago.citymapperproject.features.arrivaltimes.api;

import com.tiago.citymapperproject.features.arrivaltimes.model.ArrivalTime;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArrivalTimesApi {

  @GET("StopPoint/{naptanId}/Arrivals") Single<List<ArrivalTime>> getArrivalTimes(
      @Path("naptanId") String naptanId);
}
