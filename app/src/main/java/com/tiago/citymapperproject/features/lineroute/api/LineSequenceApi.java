package com.tiago.citymapperproject.features.lineroute.api;

import com.tiago.citymapperproject.features.lineroute.model.LineSequenceResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LineSequenceApi {

  @GET("Line/{id}/Route/Sequence/inbound") Single<LineSequenceResponse> getLineSequence(
      @Path("id") String id);
}
