package com.tiago.citymapperproject.features.stoppoints.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tiago.citymapperproject.R;
import com.tiago.citymapperproject.core.app.Navigator;
import com.tiago.citymapperproject.core.constants.LondonConstants;
import com.tiago.citymapperproject.core.view.SimpleDividerItemDecoration;
import com.tiago.citymapperproject.features.arrivaltimes.viewmodel.ArrivalTimesViewModel;
import com.tiago.citymapperproject.features.stoppoints.viewmodel.StopPointsViewModel;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import timber.log.Timber;

public class StopPointsActivity extends AppCompatActivity {

  @Inject ViewModelProvider.Factory viewModelFactory;
  @Inject Navigator navigator;

  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.progressBar) ProgressBar progressBar;

  private StopPointsAdapter stopPointsAdapter;
  private LinearLayoutManager linearLayoutManager;
  private StopPointsViewModel stopPointsViewModel;
  private ArrivalTimesViewModel arrivalTimesViewModel;
  private RxPermissions rxPermissions;
  private ReactiveLocationProvider reactiveLocationProvider;
  private CompositeDisposable subscriptions;
  private double lon = LondonConstants.LON, lat = LondonConstants.LAT;

  @SuppressLint("MissingPermission") @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nearby_stations);

    rxPermissions = new RxPermissions(this);
    reactiveLocationProvider = new ReactiveLocationProvider(this);
    subscriptions = new CompositeDisposable();

    configureDagger();
    configureButterKnife();
    configureArrivalTimesViewModel();
    configureUI();

    subscriptions.add(rxPermissions.requestEachCombined(Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION).flatMap(permission -> {
      if (permission.granted) {
        return reactiveLocationProvider.getLastKnownLocation().flatMap(location -> {
          Location result;

          if (location.distanceTo(LondonConstants.getLondonLocation()) < 150000) {
            result = LondonConstants.getLondonLocation();
          } else {
            result = location;
          }

          return Observable.just(result);
        });
      } else {
        return Observable.just(LondonConstants.getLondonLocation());
      }
    }).subscribe(location -> configureStopPointsViewModel(), throwable -> Timber.d(throwable)));
  }

  @Override protected void onDestroy() {
    subscriptions.clear();
    subscriptions.dispose();
    super.onDestroy();
  }

  private void configureDagger() {
    AndroidInjection.inject(this);
  }

  private void configureButterKnife() {
    ButterKnife.bind(this);
  }

  private void configureStopPointsViewModel() {
    stopPointsViewModel =
        ViewModelProviders.of(this, viewModelFactory).get(StopPointsViewModel.class);

    stopPointsViewModel.getStopPointsError().observe(this, stringError -> Timber.d(stringError));

    stopPointsViewModel.getStopPoints(lon, lat)
        .observe(this, stopPoints -> stopPointsAdapter.setItems(stopPoints));

    stopPointsViewModel.isLoading().observe(this, isLoading -> {
      progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
      recyclerView.setVisibility(!isLoading ? View.VISIBLE : View.GONE);
    });
  }

  private void configureArrivalTimesViewModel() {
    arrivalTimesViewModel =
        ViewModelProviders.of(this, viewModelFactory).get(ArrivalTimesViewModel.class);

    arrivalTimesViewModel.getArrivalTimesError()
        .observe(this, stringError -> Timber.d(stringError));
  }

  private void configureUI() {
    stopPointsAdapter = new StopPointsAdapter(this, this, arrivalTimesViewModel);

    linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
    recyclerView.setAdapter(stopPointsAdapter);

    subscriptions.add(stopPointsAdapter.onArrivalClick()
        .subscribe(arrivalTime -> navigator.navigateToLineSequence(this, LondonConstants.LON,
            LondonConstants.LAT, arrivalTime.getLineId(), arrivalTime.getLineName(),
            arrivalTime.getNaptanId()), throwable -> Timber.d(throwable)));
  }
}
