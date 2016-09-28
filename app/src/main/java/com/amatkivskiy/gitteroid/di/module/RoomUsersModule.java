package com.amatkivskiy.gitteroid.di.module;


import android.support.annotation.Nullable;

import com.amatkivskiy.gitteroid.di.internals.BackgroundScheduler;
import com.amatkivskiy.gitteroid.di.internals.MainThreadScheduler;
import com.amatkivskiy.gitteroid.model.RoomModel;
import com.amatkivskiy.gitteroid.data.entity.mapper.UserMapper;
import com.amatkivskiy.gitteroid.data.repository.NetworkRoomUsersRepository;
import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.interactor.rooms.GetRoomUsersUseCase;
import com.amatkivskiy.gitteroid.domain.repository.RoomUsersRepository;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class RoomUsersModule {

  private RoomModel room;

  public RoomUsersModule(RoomModel room) {
    this.room = room;
  }

  @Provides
  RoomUsersRepository NetworkRoomUsersRepository(@Nullable UserAccount account) {
    RxGitterApiClient client = new RxGitterApiClient.Builder().withAccountToken(account.token).build();

    return new NetworkRoomUsersRepository(client, new UserMapper());
  }

  @Provides
  GetRoomUsersUseCase provideGetRoomUsersUseCase(
      @BackgroundScheduler Scheduler backgroundScheduler,
      @MainThreadScheduler Scheduler mainScheduler,
      RoomUsersRepository repository) {
    Room convertedRoom = new Room(room.id, room.name, room.unreadItems, room.lastAccessTime, room.mentions);
    return new GetRoomUsersUseCase(backgroundScheduler, mainScheduler, repository, convertedRoom);
  }
}
