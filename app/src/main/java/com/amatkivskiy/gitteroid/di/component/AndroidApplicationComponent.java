package com.amatkivskiy.gitteroid.di.component;

import android.content.Context;
import android.support.annotation.Nullable;

import com.amatkivskiy.gitteroid.App;
import com.amatkivskiy.gitteroid.di.internals.BackgroundScheduler;
import com.amatkivskiy.gitteroid.di.internals.ForApplication;
import com.amatkivskiy.gitteroid.di.internals.MainThreadScheduler;
import com.amatkivskiy.gitteroid.di.module.ActivityModule;
import com.amatkivskiy.gitteroid.di.module.AndroidModule;
import com.amatkivskiy.gitteroid.di.module.UserAccountModule;
import com.amatkivskiy.gitteroid.data.prefs.ComplexPreferences;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;

import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

@Component(modules = {
    AndroidModule.class,
    UserAccountModule.class
})
@Singleton
public interface AndroidApplicationComponent {

  void inject(App application);

  @ForApplication
  Context context();

  @MainThreadScheduler
  Scheduler mainScheduler();

  @BackgroundScheduler
  Scheduler backgroundScheduler();

  @Nullable
  UserAccount userAccount();

  ComplexPreferences preferences();

  ActivityComponent plus(ActivityModule module);


}
