package com.tiago.citymapperproject.core.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.tiago.citymapperproject.core.MySchedulers;
import com.tiago.citymapperproject.core.database.CityMapperDatabase;
import com.tiago.citymapperproject.features.stoppoints.database.StopPointsDao;
import com.tiago.citymapperproject.features.stoppoints.repository.DBStopPointsRepository;
import com.tiago.citymapperproject.features.stoppoints.repository.DBStopPointsRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class PersistenceModule {

  @Provides @Singleton CityMapperDatabase provideDatabase(Context context) {
    return Room.databaseBuilder(context, CityMapperDatabase.class, "CityMapperDatabase.db").build();
  }

  @Provides @Singleton StopPointsDao provideStopPointsDao(CityMapperDatabase database) {
    return database.stopPointsDao();
  }

  @Provides @Singleton DBStopPointsRepository provideLocalStopPointsRepository(
      StopPointsDao stopPointsDao, MySchedulers schedulers) {
    return new DBStopPointsRepositoryImpl(stopPointsDao, schedulers);
  }
}
