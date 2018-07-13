package com.tiago.citymapperproject.features.lineroute.repository;

import com.tiago.citymapperproject.features.lineroute.model.LineSequenceResponse;
import io.reactivex.Single;

public interface ApiLineSequenceRepository {

  Single<LineSequenceResponse> getLineSequence(String lineId);
}
