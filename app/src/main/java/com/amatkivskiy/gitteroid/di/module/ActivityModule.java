package com.amatkivskiy.gitteroid.di.module;

import com.amatkivskiy.gitteroid.ui.activity.base.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
  private final BaseActivity activity;

  public ActivityModule(BaseActivity activity) {
    this.activity = activity;
  }

  @Provides
  BaseActivity provideActivity() {
    return activity;
  }
}