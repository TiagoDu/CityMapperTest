package com.tiago.citymapperproject.features.lineroute.repository;

import com.tiago.citymapperproject.core.MySchedulers;
import com.tiago.citymapperproject.features.lineroute.api.LineSequenceApi;
import com.tiago.citymapperproject.features.lineroute.model.LineSequenceResponse;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton public class ApiLineSequenceRepositoryImpl implements ApiLineSequenceRepository {

  private final LineSequenceApi lineSequenceApi;
  private final MySchedulers schedulers;

  @Inject
  public ApiLineSequenceRepositoryImpl(LineSequenceApi lineSequenceApi, MySchedulers schedulers) {
    this.lineSequenceApi = lineSequenceApi;
    this.schedulers = schedulers;
  }

  @Override public Single<LineSequenceResponse> getLineSequence(String lineId) {
    return lineSequenceApi.getLineSequence(lineId)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui());
  }
}
