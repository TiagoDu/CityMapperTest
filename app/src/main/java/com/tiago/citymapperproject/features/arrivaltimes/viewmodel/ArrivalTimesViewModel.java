package com.tiago.citymapperproject.features.arrivaltimes.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Pair;
import com.tiago.citymapperproject.core.MySchedulers;
import com.tiago.citymapperproject.core.SingleLiveEvent;
import com.tiago.citymapperproject.features.arrivaltimes.model.ArrivalTime;
import com.tiago.citymapperproject.features.arrivaltimes.repository.ApiArrivalTimesRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

public class ArrivalTimesViewModel extends ViewModel {

  private final ApiArrivalTimesRepository apiArrivalTimesRepository;
  private final MySchedulers schedulers;
  private Map<String, Pair<CompositeDisposable, MutableLiveData<List<ArrivalTime>>>>
      arrivalTimesObservable = new HashMap<>();
  private final SingleLiveEvent<String> arrivalTimesError = new SingleLiveEvent<>();

  @Inject public ArrivalTimesViewModel(ApiArrivalTimesRepository apiArrivalTimesRepository,
      MySchedulers schedulers) {
    this.apiArrivalTimesRepository = apiArrivalTimesRepository;
    this.schedulers = schedulers;
  }

  @Override protected void onCleared() {
    super.onCleared();

    for (Pair<CompositeDisposable, MutableLiveData<List<ArrivalTime>>> pair : arrivalTimesObservable
        .values()) {
      pair.first.clear();
      pair.first.dispose();
    }

    arrivalTimesObservable.clear();
  }

  public LiveData<List<ArrivalTime>> loadArrivals(String naptanId) {
    if (arrivalTimesObservable.get(naptanId) == null) {
      arrivalTimesObservable.put(naptanId,
          Pair.create(new CompositeDisposable(), new MutableLiveData<>()));
    }

    arrivalTimesObservable.get(naptanId).first.add(
        apiArrivalTimesRepository.getArrivalTimes(naptanId)
            .subscribeOn(schedulers.io())
            .repeatWhen(completed -> completed.delay(30000, TimeUnit.MILLISECONDS)) // every 30 secs
            .observeOn(schedulers.ui())
            .take(3)
            .subscribe(arrivalTimes -> {
              Collections.sort(arrivalTimes,
                  (o1, o2) -> o1.getTimeToStation() - o2.getTimeToStation());
              arrivalTimesObservable.get(naptanId).second.setValue(arrivalTimes);
            }, throwable -> arrivalTimesError.setValue(throwable.getMessage())));

    return arrivalTimesObservable.get(naptanId).second;
  }

  public void removeArrivals(String naptanId) {
    Pair<CompositeDisposable, MutableLiveData<List<ArrivalTime>>> pair =
        arrivalTimesObservable.get(naptanId);
    if (pair != null) {
      pair.first.clear();
      pair.first.dispose();
    }
  }

  public LiveData<String> getArrivalTimesError() {
    return arrivalTimesError;
  }
}
