package com.tiago.citymapperproject.features.lineroute.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tiago.citymapperproject.R;
import com.tiago.citymapperproject.core.constants.LondonConstants;
import com.tiago.citymapperproject.features.lineroute.viewmodel.LineSequenceViewModel;
import dagger.android.AndroidInjection;
import javax.inject.Inject;
import timber.log.Timber;

public class LineSequenceActivity extends AppCompatActivity {

  public static final String EXTRA_USER_LON = "EXTRA_USER_LON";
  public static final String EXTRA_USER_LAT = "EXTRA_USER_LAT";
  public static final String EXTRA_LINE_ID = "EXTRA_LINE_ID";
  public static final String EXTRA_LINE_NAME = "EXTRA_LINE_NAME";
  public static final String EXTRA_STATION_ID = "EXTRA_STATION_ID";

  public static Intent getCallingIntent(Context context, double lon, double lat, String lineId,
      String lineName, String stationId) {
    Intent intent = new Intent(context, LineSequenceActivity.class);
    intent.putExtra(EXTRA_USER_LON, lon);
    intent.putExtra(EXTRA_USER_LAT, lat);
    intent.putExtra(EXTRA_LINE_ID, lineId);
    intent.putExtra(EXTRA_LINE_NAME, lineName);
    intent.putExtra(EXTRA_STATION_ID, stationId);
    return intent;
  }

  @Inject ViewModelProvider.Factory viewModelFactory;

  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.progressBar) ProgressBar progressBar;

  private double lon, lat;
  private String lineId, lineName, stationId;
  private LineSequenceAdapter lineSequenceAdapter;
  private LinearLayoutManager linearLayoutManager;

  private LineSequenceViewModel lineSequenceViewModel;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_line_sequence);

    configureExtras(getIntent());
    configureActionBar();
    configureDagger();
    configureButterKnife();
    configureUI();
    configureViewModel();
  }

  private void configureExtras(Intent intent) {
    lon = intent.getDoubleExtra(EXTRA_USER_LON, LondonConstants.LON);
    lat = intent.getDoubleExtra(EXTRA_USER_LAT, LondonConstants.LAT);
    lineId = intent.getStringExtra(EXTRA_LINE_ID);
    lineName = intent.getStringExtra(EXTRA_LINE_NAME);
    stationId = intent.getStringExtra(EXTRA_STATION_ID);
  }

  private void configureActionBar() {
    getSupportActionBar().setIcon(null);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(lineName);
  }

  private void configureDagger() {
    AndroidInjection.inject(this);
  }

  private void configureButterKnife() {
    ButterKnife.bind(this);
  }

  private void configureViewModel() {
    lineSequenceViewModel =
        ViewModelProviders.of(this, viewModelFactory).get(LineSequenceViewModel.class);

    lineSequenceViewModel.getLineSequenceError()
        .observe(this, stringError -> Timber.d(stringError));

    lineSequenceViewModel.getLineStation(lineId, stationId, lon, lat)
        .observe(this, stations -> lineSequenceAdapter.setItems(stations));

    lineSequenceViewModel.isLoading().observe(this, isLoading -> {
      progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
      recyclerView.setVisibility(!isLoading ? View.VISIBLE : View.GONE);
    });
  }

  private void configureUI() {
    lineSequenceAdapter = new LineSequenceAdapter(this);

    linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(lineSequenceAdapter);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /*
    PUBLIC
   */

  @Override public void finish() {
    super.finish();
    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
  }
}
