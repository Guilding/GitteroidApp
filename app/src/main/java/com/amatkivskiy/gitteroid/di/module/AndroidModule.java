package com.amatkivskiy.gitteroid.di.module;

import android.content.Context;

import com.amatkivskiy.gitteroid.App;
import com.amatkivskiy.gitteroid.di.internals.BackgroundScheduler;
import com.amatkivskiy.gitteroid.di.internals.ForApplication;
import com.amatkivskiy.gitteroid.di.internals.MainThreadScheduler;
import com.amatkivskiy.gitteroid.data.prefs.ComplexPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class AndroidModule {

  private final App application;

  public AndroidModule(App application) {
    this.application = application;
  }

  @Provides
  @ForApplication
  @Singleton
  Context provideApplicationContext() {
    return application;
  }

  @Provides
  @MainThreadScheduler
  @Singleton
  Scheduler provideMainThreadScheduler() {
    return AndroidSchedulers.mainThread();
  }

  @Provides
  @BackgroundScheduler
  @Singleton
  Scheduler provideBackgroundScheduler() {
    return Schedulers.newThread();
  }

  @Provides
  @Singleton
  ComplexPreferences providePreferences() {
    return ComplexPreferences.getComplexPreferences(application);
  }
}