package com.tiago.citymapperproject.core.di;

import android.content.Context;
import com.tiago.citymapperproject.core.MySchedulers;
import com.tiago.citymapperproject.core.app.App;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(includes = { NetworkModule.class, PersistenceModule.class, ViewModelModule.class })
public class AppModule {

  @Provides Context provideContext(App application) {
    return application.getApplicationContext();
  }

  @Singleton @Provides MySchedulers schedulersFacade() {
    return new MySchedulers();
  }
}
