package com.tiago.citymapperproject.core.di;

import com.tiago.citymapperproject.core.app.App;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

@Singleton @Component(modules = {
    AndroidSupportInjectionModule.class, AppModule.class, ActivityModule.class
}) public interface AppComponent {

  @Component.Builder interface Builder {
    @BindsInstance Builder application(App application);

    AppComponent build();
  }

  void inject(App app);
}
