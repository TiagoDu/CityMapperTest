package com.tiago.citymapperproject.core.di;

import com.tiago.citymapperproject.features.lineroute.view.LineSequenceActivity;
import com.tiago.citymapperproject.features.stoppoints.view.StopPointsActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module public abstract class ActivityModule {

  @ContributesAndroidInjector abstract StopPointsActivity contributeStopPointsActivity();

  @ContributesAndroidInjector abstract LineSequenceActivity contributeLineSequenceActivity();
}
