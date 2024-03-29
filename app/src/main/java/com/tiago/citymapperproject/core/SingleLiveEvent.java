package com.tiago.citymapperproject.core;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * <p>
 * Note that only one observer is going to be notified of changes.
 */

// From: https://github.com/googlesamples/android-architecture/blob/dev-todo-mvvm-live/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/SingleLiveEvent.java

public class SingleLiveEvent<T> extends MutableLiveData<T> {

  private static final String TAG = "SingleLiveEvent";

  private final AtomicBoolean pending = new AtomicBoolean(false);

  @MainThread public void observe(LifecycleOwner owner, final Observer<T> observer) {

    if (hasActiveObservers()) {
      Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
    }

    // Observe the internal MutableLiveData
    super.observe(owner, new Observer<T>() {
      @Override public void onChanged(@Nullable T t) {
        if (pending.compareAndSet(true, false)) {
          observer.onChanged(t);
        }
      }
    });
  }

  @MainThread public void setValue(@Nullable T t) {
    pending.set(true);
    super.setValue(t);
  }

  /**
   * Used for cases where T is Void, to make calls cleaner.
   */
  @MainThread public void call() {
    setValue(null);
  }
}