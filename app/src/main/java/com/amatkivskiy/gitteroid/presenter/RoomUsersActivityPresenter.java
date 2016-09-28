package com.amatkivskiy.gitteroid.presenter;

import com.amatkivskiy.gitteroid.di.component.ActivityComponent;
import com.amatkivskiy.gitteroid.di.component.RoomUsersComponent;
import com.amatkivskiy.gitteroid.di.module.RoomUsersModule;
import com.amatkivskiy.gitteroid.model.RoomModel;
import com.amatkivskiy.gitteroid.presenter.base.BaseActivityPresenter;
import com.amatkivskiy.gitteroid.ui.activity.RoomUsersActivity;
import com.amatkivskiy.gitteroid.domain.entity.User;
import com.amatkivskiy.gitteroid.domain.interactor.rooms.GetRoomUsersUseCase;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

public class RoomUsersActivityPresenter extends BaseActivityPresenter<RoomUsersActivity> {

  private RoomModel room;

  @Inject
  GetRoomUsersUseCase roomUsersUseCase;

  @Override
  public void initComponentAndInjectMembers(ActivityComponent rootComponent) {
    RoomUsersComponent component = rootComponent.plus(new RoomUsersModule(this.room));
    component.inject(this);
  }

  @Override
  protected void onTakeView(RoomUsersActivity view) {
    this.room = view.getRoom();

    super.onTakeView(view);

    loadUsers();
  }

  private void loadUsers() {
    getView().onLoadingStarted();

    roomUsersUseCase.execute(new Subscriber<List<User>>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        getView().onLoadingFailed();
      }

      @Override
      public void onNext(List<User> users) {
        getView().showUsers(users);
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    roomUsersUseCase.unsubscribe();
  }
}
