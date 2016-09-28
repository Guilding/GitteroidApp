package com.amatkivskiy.gitteroid.di.component;

import com.amatkivskiy.gitteroid.di.module.UserAccountManagerModule;
import com.amatkivskiy.gitteroid.presenter.LoginActivityPresenter;
import com.amatkivskiy.gitteroid.domain.interactor.user.DeleteUserUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.user.GetUserInfoFromInternetUseCase;

import dagger.Subcomponent;

@Subcomponent(modules = UserAccountManagerModule.class)
public interface UserAccountManagerComponent {
  void inject(LoginActivityPresenter presenter);

  GetUserInfoFromInternetUseCase saveAccountUserCase();
  DeleteUserUseCase deleteUserUseCase();
}
