package com.amatkivskiy.gitteroid.di.component;

import com.amatkivskiy.gitteroid.di.module.MessageModule;
import com.amatkivskiy.gitteroid.mapper.MessageModelMapper;
import com.amatkivskiy.gitteroid.presenter.ChatRoomViewPresenter;
import com.amatkivskiy.gitteroid.domain.interactor.messages.GetRoomMessageStreamUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.messages.GetRoomMessagesUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.messages.GetRoomUnreadMessagesUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.messages.SendMessageUseCase;

import dagger.Subcomponent;

@Subcomponent(
    modules = MessageModule.class
)
public interface MessageComponent {
  void inject(ChatRoomViewPresenter chatRoomViewPresenter);

  GetRoomMessagesUseCase roomMessagesUseCase();

  SendMessageUseCase sendMessageUseCase();

  GetRoomMessageStreamUseCase getRoomMessageStreamUseCase();

  GetRoomUnreadMessagesUseCase getRoomUnreadMessagesUseCase();

  MessageModelMapper messageModelMapper();
}
