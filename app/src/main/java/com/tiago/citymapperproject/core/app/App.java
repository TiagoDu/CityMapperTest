package com.tiago.citymapperproject.core.app;

import android.app.Activity;
import android.app.Application;
import com.tiago.citymapperproject.BuildConfig;
import com.tiago.citymapperproject.core.di.DaggerAppComponent;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import javax.inject.Inject;
import timber.log.Timber;

public class App extends Application implements HasActivityInjector {

  @Inject DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

  @Override public void onCreate() {
    super.onCreate();
    initDagger();
    initTimber();
  }

  @Override public DispatchingAndroidInjector<Activity> activityInjector() {
    return dispatchingAndroidInjector;
  }

  private void initTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  private void initDagger() {
    DaggerAppComponent.builder().application(this).build().inject(this);
  }
}
