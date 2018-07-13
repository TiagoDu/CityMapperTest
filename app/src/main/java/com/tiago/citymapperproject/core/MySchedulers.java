package com.tiago.citymapperproject.core;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Singleton;

@Singleton public class MySchedulers {

  public Scheduler io() {
    return Schedulers.io();
  }

  public Scheduler computation() {
    return Schedulers.computation();
  }

  public Scheduler ui() {
    return AndroidSchedulers.mainThread();
  }
}