package com.tiago.citymapperproject.core.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tiago.citymapperproject.R;
import com.tiago.citymapperproject.features.lineroute.view.LineSequenceActivity;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton public class Navigator {

  private Context appContext;

  @Inject public Navigator(Context appContext) {
    this.appContext = appContext;
  }

  public void navigateToLineSequence(Activity activity, double lon, double lat, String lineId,
      String lineName, String stationId) {
    Intent intent =
        LineSequenceActivity.getCallingIntent(activity, lon, lat, lineId, lineName, stationId);
    activity.startActivity(intent);
    activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
  }
}
