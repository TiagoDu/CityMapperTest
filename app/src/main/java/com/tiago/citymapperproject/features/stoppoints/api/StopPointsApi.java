package com.tiago.citymapperproject.features.stoppoints.api;

import com.tiago.citymapperproject.features.stoppoints.model.StopPointResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StopPointsApi {

  String STOP_TYPES = "NaptanMetroStation";
  int RADIUS = 1000; // 1KM

  @GET("StopPoint") Single<StopPointResponse> nearbyStopPoint(@Query("lon") Double lon,
      @Query("lat") Double lat, @Query("stopTypes") String stopTypes, @Query("radius") int radius);
}
