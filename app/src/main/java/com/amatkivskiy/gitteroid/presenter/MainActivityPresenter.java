package com.amatkivskiy.gitteroid.presenter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.amatkivskiy.gitteroid.di.component.ActivityComponent;
import com.amatkivskiy.gitteroid.di.component.RoomsComponent;
import com.amatkivskiy.gitteroid.di.component.UserAccountManagerComponent;
import com.amatkivskiy.gitteroid.di.internals.ForApplication;
import com.amatkivskiy.gitteroid.di.module.RoomsModule;
import com.amatkivskiy.gitteroid.di.module.UserAccountManagerModule;
import com.amatkivskiy.gitteroid.mapper.RoomsModelMapper;
import com.amatkivskiy.gitteroid.model.RoomModel;
import com.amatkivskiy.gitteroid.presenter.base.BaseActivityPresenter;
import com.amatkivskiy.gitteroid.ui.activity.LoginActivity;
import com.amatkivskiy.gitteroid.ui.activity.MainActivity;
import com.amatkivskiy.gitteroid.ui.activity.RoomUsersActivityIntentBuilder;
import com.amatkivskiy.gitteroid.ui.activity.SettingsActivity;
import com.amatkivskiy.gitteroid.util.RoomsComparatorUtil;
import com.amatkivskiy.gitteroid.data.LoggingSubscriber;
import com.amatkivskiy.gitteroid.data.prefs.ComplexPreferences;
import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.interactor.rooms.GetUserRoomsUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.user.DeleteUserUseCase;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.Subcomponent;
import icepick.State;
import rx.functions.Func1;

public class MainActivityPresenter extends BaseActivityPresenter<MainActivity> {

  public static final int DRAWER_ITEM_ALL_IDENTIFIER = -2;
  public static final int DRAWER_ITEM_SETTINGS_IDENTIFIER = -4;
  public static final int DRAWER_ITEM_SIGN_OUT_IDENTIFIER = -5;

  @Inject
  GetUserRoomsUseCase getUserRooms;

  @Nullable
  @Inject
  UserAccount userAccount;

  @Inject
  RoomsModelMapper roomsModelMapper;
  @Inject
  DeleteUserUseCase deleteUserUseCase;

  @Inject
  @ForApplication
  Context context;

  @Inject
  ComplexPreferences preferences;

  @State
  ArrayList<RoomModel> userRooms;

  @Override
  public void initComponentAndInjectMembers(ActivityComponent rootComponent) {
    rootComponent.plus(new RoomsModule(), new UserAccountManagerModule())
        .inject(this);
  }

  public void signOut() {
    deleteUserUseCase.execute(new LoggingSubscriber<Boolean>() {
      @Override
      public void onCompleted() {
        super.onCompleted();
        startActivity(LoginActivity.createIntent(getView()));
        finishActivity();
      }
    });
  }

  public void onRoomSelected(int roomPosition) {
    RoomModel selectedRoom = userRooms.get(roomPosition);
    getView().showRoom(selectedRoom);
  }

  @Override
  protected void onTakeView(MainActivity view) {
    super.onTakeView(view);

    getView().initDrawerWithAccount(userAccount);
    if (userRooms == null) {
      loadRooms();
    } else {
      getView().showDrawerRooms(userRooms.subList(0, preferences.getNumberOfRoomsInDrawer()));
    }
  }

  @Override
  protected void onDropView() {
    super.onDropView();
    getUserRooms.unsubscribe();
    deleteUserUseCase.unsubscribe();
  }

  private void loadRooms() {
    getView().onLoadingStarted();

    getUserRooms.sortBy(new Func1<List<Room>, List<Room>>() {
      @Override
      public List<Room> call(List<Room> rooms) {
        Collections.reverse(rooms);
        Collections.sort(rooms, RoomsComparatorUtil.getComplexRoomComparator());
        return rooms;
      }
    });

    getUserRooms.execute(new LoggingSubscriber<List<Room>>() {
      @Override
      public void onNext(List<Room> rooms) {
        super.onNext(rooms);
        userRooms = (ArrayList<RoomModel>) roomsModelMapper.transform(rooms);
        getView().showDrawerRooms(userRooms.subList(0, preferences.getNumberOfRoomsInDrawer()));
        getView().showRoom(userRooms.get(0));
        getView().selectDrawerItem(getIdentifierForRoomPosition(0));

        getView().onLoadingFinished();
      }

      @Override
      public void onError(Throwable e) {
        super.onError(e);
        getView().onLoadingFailed();
        getView().showError(e);
      }
    }, userAccount.id);
  }

  public void onAllRoomsSelected() {
    getView().showAllRoomsView(userRooms);
  }

  public void onRoomFragmentSelected(RoomModel room) {
    if (userRooms.indexOf(room) < preferences.getNumberOfRoomsInDrawer()) {
      getView().selectDrawerItem(getIdentifierForRoomPosition(userRooms.indexOf(room)));
    } else {
      getView().selectDrawerItem(-2);
    }
  }

  public int getIdentifierForRoomPosition(int position) {
    return ++position;
  }

  public int getPositionForRoomIdentifier(int identifier) {
    return --identifier;
  }

  public void onDrawerItemSelected(IDrawerItem drawerItem) {
    int identifier = (int) drawerItem.getIdentifier();

    if (identifier == DRAWER_ITEM_SIGN_OUT_IDENTIFIER) {
      signOut();
    } else if (identifier == DRAWER_ITEM_ALL_IDENTIFIER) {
      onAllRoomsSelected();
    } else if (identifier == DRAWER_ITEM_SETTINGS_IDENTIFIER) {
      onSettingsSelected();
    } else {
      //If room item was selected.
      int position = getPositionForRoomIdentifier(identifier);
      onRoomSelected(position);
    }
  }

  private void onSettingsSelected() {
    startActivity(SettingsActivity.createIntent(context));
  }

  public void showRoomUsersScreen() {
    RoomModel room = getView().getCurrentSelectedRoomPosition();
    startActivity(new RoomUsersActivityIntentBuilder(room)
                      .build(context));
  }

  public void onRefreshClicked() {
    loadRooms();
  }

  @Subcomponent(modules = {
      RoomsModule.class,
      UserAccountManagerModule.class
  })
  public interface MainActivityPresenterComponent
      extends RoomsComponent, UserAccountManagerComponent {

  }
}
