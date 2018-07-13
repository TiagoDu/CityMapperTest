package com.tiago.citymapperproject.features.lineroute.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.tiago.citymapperproject.core.SingleLiveEvent;
import com.tiago.citymapperproject.core.utils.LocationUtils;
import com.tiago.citymapperproject.features.lineroute.model.LineStation;
import com.tiago.citymapperproject.features.lineroute.model.LineStopPoint;
import com.tiago.citymapperproject.features.lineroute.model.LineStopPointSequence;
import com.tiago.citymapperproject.features.lineroute.model.LineStopStation;
import com.tiago.citymapperproject.features.lineroute.repository.ApiLineSequenceRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class LineSequenceViewModel extends ViewModel {

  private final ApiLineSequenceRepository apiLineSequenceRepository;
  private final MutableLiveData<List<LineStation>> stations = new MutableLiveData<>();
  private final SingleLiveEvent<String> lineSequenceError = new SingleLiveEvent<>();
  private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
  private CompositeDisposable subscriptions = new CompositeDisposable();

  @Inject public LineSequenceViewModel(ApiLineSequenceRepository apiLineSequenceRepository) {
    this.apiLineSequenceRepository = apiLineSequenceRepository;
  }

  @Override protected void onCleared() {
    super.onCleared();
    subscriptions.clear();
  }

  public LiveData<List<LineStation>> getLineStation(String lineId, String stationId, double lon,
      double lat) {
    subscriptions.add(apiLineSequenceRepository.getLineSequence(lineId)
        .doOnSubscribe(disposable -> loading.setValue(true))
        .subscribe(response -> {
          List<LineStation> stationsList = new ArrayList<>();
          for (LineStopPointSequence lineStopPointSequence : response.getStopPointSequences()) {
            List<LineStopPoint> stopPoints = lineStopPointSequence.getStopPoint();

            for (LineStopPoint stopPoint : stopPoints) {
              for (LineStopStation station : response.getStations()) {
                if ((stopPoint != null && stopPoint.getId().equals(station.getId())) ||
                    (stopPoint.getParentId() != null &&
                        stopPoint.getParentId().equals(station.getId()))) {
                  stationsList.add(
                      new LineStation(station.getName(), station.getId().equals(stationId),
                          LocationUtils.getDistance(station.getLon(), station.getLat(), lon, lat)));
                }
              }
            }
          }

          LineStation minDistance = null;
          for (LineStation lineStopStation : stationsList) {
            minDistance =
                (minDistance == null || lineStopStation.getDistance() < minDistance.getDistance())
                    ? lineStopStation : minDistance;
          }
          minDistance.setClosest(true);

          stations.setValue(stationsList);
          loading.setValue(false);
        }, throwable -> lineSequenceError.setValue(throwable.getMessage())));

    return stations;
  }

  public SingleLiveEvent<String> getLineSequenceError() {
    return lineSequenceError;
  }

  public MutableLiveData<Boolean> isLoading() {
    return loading;
  }
}
