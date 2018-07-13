package com.tiago.citymapperproject.core.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.tiago.citymapperproject.core.viewmodel.FactoryViewModel;
import com.tiago.citymapperproject.features.arrivaltimes.viewmodel.ArrivalTimesViewModel;
import com.tiago.citymapperproject.features.lineroute.viewmodel.LineSequenceViewModel;
import com.tiago.citymapperproject.features.stoppoints.viewmodel.StopPointsViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module public abstract class ViewModelModule {

  @Binds abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);

  @Binds @IntoMap @ViewModelKey(StopPointsViewModel.class)
  abstract ViewModel bindStopPointViewModel(StopPointsViewModel stopPointsViewModel);

  @Binds @IntoMap @ViewModelKey(ArrivalTimesViewModel.class)
  abstract ViewModel bindArrivalTimesViewModel(ArrivalTimesViewModel arrivalTimesViewModel);

  @Binds @IntoMap @ViewModelKey(LineSequenceViewModel.class)
  abstract ViewModel bindLineSequenceViewModel(LineSequenceViewModel lineSequenceViewModel);
}