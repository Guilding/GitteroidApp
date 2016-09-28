package com.amatkivskiy.gitteroid.di.component;

import com.amatkivskiy.gitteroid.di.module.RoomUsersModule;
import com.amatkivskiy.gitteroid.presenter.RoomUsersActivityPresenter;
import com.amatkivskiy.gitteroid.domain.interactor.rooms.GetRoomUsersUseCase;

import dagger.Subcomponent;

@Subcomponent(modules = RoomUsersModule.class)
public interface RoomUsersComponent {

  void inject(RoomUsersActivityPresenter roomUsersActivityPresenter);

  GetRoomUsersUseCase getRoomUsersUseCase();
}
