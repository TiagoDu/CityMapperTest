package com.tiago.citymapperproject.core.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiago.citymapperproject.BuildConfig;
import com.tiago.citymapperproject.core.MySchedulers;
import com.tiago.citymapperproject.features.arrivaltimes.api.ArrivalTimesApi;
import com.tiago.citymapperproject.features.arrivaltimes.repository.ApiArrivalTimesRepository;
import com.tiago.citymapperproject.features.arrivaltimes.repository.ApiArrivalTimesRepositoryImpl;
import com.tiago.citymapperproject.features.lineroute.api.LineSequenceApi;
import com.tiago.citymapperproject.features.lineroute.repository.ApiLineSequenceRepository;
import com.tiago.citymapperproject.features.lineroute.repository.ApiLineSequenceRepositoryImpl;
import com.tiago.citymapperproject.features.stoppoints.api.StopPointsApi;
import com.tiago.citymapperproject.features.stoppoints.repository.ApiStopPointsRepository;
import com.tiago.citymapperproject.features.stoppoints.repository.ApiStopPointsRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class NetworkModule {

  @Provides Gson provideGson() {
    return new GsonBuilder().create();
  }

  @Provides Retrofit provideRetrofit(Gson gson) {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

    Retrofit retrofit =
        new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .baseUrl(BuildConfig.API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    return retrofit;
  }

  @Provides @Singleton ApiStopPointsRepository provideApiStopPointsRepository(Retrofit retrofit,
      MySchedulers schedulers) {
    return new ApiStopPointsRepositoryImpl(retrofit.create(StopPointsApi.class), schedulers);
  }

  @Provides @Singleton ApiArrivalTimesRepository provideApiArrivalTimesRepository(Retrofit retrofit,
      MySchedulers schedulers) {
    return new ApiArrivalTimesRepositoryImpl(retrofit.create(ArrivalTimesApi.class), schedulers);
  }

  @Provides @Singleton ApiLineSequenceRepository provideApiLineSequenceRepository(Retrofit retrofit,
      MySchedulers schedulers) {
    return new ApiLineSequenceRepositoryImpl(retrofit.create(LineSequenceApi.class), schedulers);
  }
}
