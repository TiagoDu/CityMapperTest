package com.tiago.citymapperproject.features.stoppoints.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.tiago.citymapperproject.core.SingleLiveEvent;
import com.tiago.citymapperproject.features.stoppoints.model.StopPoint;
import com.tiago.citymapperproject.features.stoppoints.repository.ApiStopPointsRepository;
import com.tiago.citymapperproject.features.stoppoints.repository.DBStopPointsRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import javax.inject.Inject;

public class StopPointsViewModel extends ViewModel {

  private final ApiStopPointsRepository apiStopPointsRepository;
  private final DBStopPointsRepository dbStopPointsRepository;
  private final MutableLiveData<List<StopPoint>> stopPoints = new MutableLiveData<>();
  private final SingleLiveEvent<String> stopPointsError = new SingleLiveEvent<>();
  private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
  private CompositeDisposable subscriptions = new CompositeDisposable();

  @Inject public StopPointsViewModel(DBStopPointsRepository dbStopPointsRepository,
      ApiStopPointsRepository apiStopPointsRepository) {
    this.dbStopPointsRepository = dbStopPointsRepository;
    this.apiStopPointsRepository = apiStopPointsRepository;
  }

  @Override protected void onCleared() {
    super.onCleared();
    subscriptions.clear();
  }

  public LiveData<List<StopPoint>> getStopPoints(Double lon, Double lat) {
    subscriptions.add(dbStopPointsRepository.getStopPoints(lon, lat)
        .subscribe(dbStopPoints -> stopPoints.setValue(dbStopPoints),
            throwable -> stopPointsError.setValue(throwable.getMessage())));

    subscriptions.add(apiStopPointsRepository.getStopPoints(lon, lat)
        .doOnSubscribe(disposable -> loading.setValue(true))
        .subscribe(response -> {
          stopPoints.setValue(response);
          loading.setValue(false);
        }, throwable -> stopPointsError.setValue(throwable.getMessage())));

    return stopPoints;
  }

  public SingleLiveEvent<String> getStopPointsError() {
    return stopPointsError;
  }

  public MutableLiveData<Boolean> isLoading() {
    return loading;
  }
}
