package com.amatkivskiy.gitteroid.di.component;

import com.amatkivskiy.gitteroid.di.module.RoomsModule;
import com.amatkivskiy.gitteroid.mapper.RoomsModelMapper;
import com.amatkivskiy.gitteroid.presenter.MainActivityPresenter;
import com.amatkivskiy.gitteroid.domain.interactor.rooms.GetUserRoomsUseCase;

import dagger.Subcomponent;

@Subcomponent(modules = RoomsModule.class)
public interface RoomsComponent {
  void inject(MainActivityPresenter presenter);

  GetUserRoomsUseCase userRoomsUseCase();

  RoomsModelMapper roomsModelMapper();
}
