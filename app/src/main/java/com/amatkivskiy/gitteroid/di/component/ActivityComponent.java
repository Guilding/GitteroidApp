package com.amatkivskiy.gitteroid.di.component;

import com.amatkivskiy.gitteroid.di.module.ActivityModule;
import com.amatkivskiy.gitteroid.di.module.MessageModule;
import com.amatkivskiy.gitteroid.di.module.RoomUsersModule;
import com.amatkivskiy.gitteroid.di.module.RoomsModule;
import com.amatkivskiy.gitteroid.di.module.UserAccountManagerModule;
import com.amatkivskiy.gitteroid.presenter.MainActivityPresenter.MainActivityPresenterComponent;
import com.amatkivskiy.gitteroid.presenter.PreferenceFragmentPresenter;
import com.amatkivskiy.gitteroid.ui.activity.base.BaseActivity;

import dagger.Subcomponent;

@Subcomponent(
    modules = ActivityModule.class
)
public interface ActivityComponent {
  MessageComponent plus(MessageModule module);
  MainActivityPresenterComponent plus(RoomsModule module, UserAccountManagerModule managerModule);
  UserAccountManagerComponent plus(UserAccountManagerModule module);
  RoomUsersComponent plus(RoomUsersModule module);

  BaseActivity activity();

  void inject(PreferenceFragmentPresenter presenter);
}
