package com.amatkivskiy.gitteroid.di.module;


import android.support.annotation.Nullable;

import com.amatkivskiy.gitteroid.di.internals.BackgroundScheduler;
import com.amatkivskiy.gitteroid.di.internals.MainThreadScheduler;
import com.amatkivskiy.gitteroid.mapper.MessageModelMapper;
import com.amatkivskiy.gitteroid.model.RoomModel;
import com.amatkivskiy.gitteroid.data.entity.mapper.MessagesMapper;
import com.amatkivskiy.gitteroid.data.entity.mapper.UserInfoMapper;
import com.amatkivskiy.gitteroid.data.repository.NetworkRoomMessagesRepository;
import com.amatkivskiy.gitteroid.data.repository.NetworkStreamingMessageRepository;
import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.interactor.messages.GetRoomMessageStreamUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.messages.GetRoomMessagesUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.messages.GetRoomUnreadMessagesUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.messages.MarkRoomMessagesReadUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.messages.SendMessageUseCase;
import com.amatkivskiy.gitteroid.domain.repository.MessageRepository;
import com.amatkivskiy.gitteroid.domain.repository.StreamingMessageRepository;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterStreamingApiClient;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import rx.Scheduler;

@Module
public class MessageModule {

  private RoomModel room;

  public MessageModule(RoomModel room) {
    this.room = room;
  }

  @Provides
  MessageRepository NetworkRoomMessagesRepository(@Nullable UserAccount account,
                                                  UserInfoMapper userInfoMapper) {
    RxGitterApiClient
        client =
        new RxGitterApiClient.Builder().withAccountToken(account.token).withLogLevel(RestAdapter.LogLevel.FULL).build();

    return new NetworkRoomMessagesRepository(client, new MessagesMapper(userInfoMapper));
  }

  @Provides
  StreamingMessageRepository provideStreamingMessageRepository(@Nullable UserAccount account,
                                                  UserInfoMapper userInfoMapper) {
    RxGitterStreamingApiClient client =
        new RxGitterStreamingApiClient.Builder().withAccountToken(account.token).build();

    return new NetworkStreamingMessageRepository(client, new MessagesMapper(userInfoMapper));
  }

  @Provides
  GetRoomMessagesUseCase provideGetRoomMessagesUseCase(
      @BackgroundScheduler Scheduler backgroundScheduler,
      @MainThreadScheduler Scheduler mainScheduler,
      MessageRepository repository) {
    Room
        convertedRoom =
        new Room(room.id, room.name, room.unreadItems, room.lastAccessTime, room.mentions);
    return new GetRoomMessagesUseCase(backgroundScheduler, mainScheduler, repository,
                                      convertedRoom);
  }

  @Provides
  SendMessageUseCase provideSendMessageUseCase(
      @BackgroundScheduler Scheduler backgroundScheduler,
      @MainThreadScheduler Scheduler mainScheduler,
      MessageRepository repository) {
    Room
        convertedRoom =
        new Room(room.id, room.name, room.unreadItems, room.lastAccessTime, room.mentions);
    return new SendMessageUseCase(backgroundScheduler, mainScheduler, repository, convertedRoom);
  }

  @Provides
  GetRoomMessageStreamUseCase provideGetRoomMessageStreamUseCase(
      @BackgroundScheduler Scheduler backgroundScheduler,
      @MainThreadScheduler Scheduler mainScheduler,
      StreamingMessageRepository repository) {
    Room convertedRoom = new Room(room.id, room.name, room.unreadItems, room.lastAccessTime, room.mentions);
    return new GetRoomMessageStreamUseCase(backgroundScheduler, mainScheduler, repository, convertedRoom);
  }

  @Provides
  GetRoomUnreadMessagesUseCase provideGetRoomUnreadMessagesUseCase(
      @BackgroundScheduler Scheduler backgroundScheduler,
      @MainThreadScheduler Scheduler mainScheduler,
      MessageRepository repository,
      @Nullable UserAccount userAccount) {
    Room convertedRoom = new Room(room.id, room.name, room.unreadItems, room.lastAccessTime, room.mentions);
    return new GetRoomUnreadMessagesUseCase(backgroundScheduler, mainScheduler, repository, convertedRoom, userAccount);
  }

  @Provides
  MarkRoomMessagesReadUseCase provideMarkRoomMessagesReadUseCase(
      @BackgroundScheduler Scheduler backgroundScheduler,
      @MainThreadScheduler Scheduler mainScheduler,
      MessageRepository repository,
      @Nullable UserAccount userAccount) {
    Room convertedRoom = new Room(room.id, room.name, room.unreadItems, room.lastAccessTime, room.mentions);
    return new MarkRoomMessagesReadUseCase(backgroundScheduler, mainScheduler, repository, convertedRoom, userAccount);
  }

  @Provides
  MessageModelMapper provideMessageModelMapper() {
    return new MessageModelMapper();
  }
}
