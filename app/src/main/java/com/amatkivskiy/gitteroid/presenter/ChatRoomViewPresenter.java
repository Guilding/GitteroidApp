package com.amatkivskiy.gitteroid.presenter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.amatkivskiy.gitteroid.di.component.ActivityComponent;
import com.amatkivskiy.gitteroid.di.component.MessageComponent;
import com.amatkivskiy.gitteroid.di.module.MessageModule;
import com.amatkivskiy.gitteroid.mapper.MessageModelMapper;
import com.amatkivskiy.gitteroid.model.MessageModel;
import com.amatkivskiy.gitteroid.model.RoomModel;
import com.amatkivskiy.gitteroid.presenter.base.BaseViewPresenter;
import com.amatkivskiy.gitteroid.ui.activity.base.BaseActivity;
import com.amatkivskiy.gitteroid.ui.view.ChatRoomView;
import com.amatkivskiy.gitteroid.data.LoggingSubscriber;
import com.amatkivskiy.gitteroid.domain.entity.GetMessagesConfig;
import com.amatkivskiy.gitteroid.domain.entity.Message;
import com.amatkivskiy.gitteroid.domain.entity.UnReadMessagesIds;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.interactor.DefaultSubscriber;
import com.amatkivskiy.gitteroid.domain.interactor.messages.GetRoomMessageStreamUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.messages.GetRoomMessagesUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.messages.GetRoomUnreadMessagesUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.messages.MarkRoomMessagesReadUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.messages.SendMessageUseCase;
import com.amatkivskiy.gitter.sdk.model.response.BooleanResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import icepick.State;

public class ChatRoomViewPresenter extends BaseViewPresenter<ChatRoomView> {

  @State
  RoomModel room;

  @State
  ArrayList<MessageModel> messages;

  @Inject
  GetRoomMessagesUseCase getRoomMessagesUseCase;

  @Inject
  SendMessageUseCase sendMessageUseCase;

  @Inject
  GetRoomMessageStreamUseCase getRoomMessageStreamUseCase;

  @Inject
  GetRoomUnreadMessagesUseCase getRoomUnreadMessagesUseCase;

  @Inject
  MarkRoomMessagesReadUseCase markRoomMessagesReadUseCase;

  @Inject
  MessageModelMapper messageModelMapper;

  @Inject
  BaseActivity activity;

  @Inject
  @Nullable
  UserAccount userAccount;

  @Override
  public void initComponentAndInjectMembers(ActivityComponent activityComponent) {
    MessageComponent component = activityComponent.plus(new MessageModule(this.room));

    component.inject(this);
  }

  @Override
  public void takeView(ChatRoomView view) {
    this.room = view.getRoom();

    super.takeView(view);

    if (messages == null) {
      loadMessages();
      markUnReadIfRequired();
    } else {
      getView().showMessages(messages);
    }

    subscribeForMessageStream();
  }

  private void markUnReadIfRequired() {
    if (room.unreadItems > 0) {
      getRoomUnreadMessagesUseCase.execute(new DefaultSubscriber<UnReadMessagesIds>() {
        @Override
        public void onNext(UnReadMessagesIds unReadMessagesIds) {
          markRoomMessagesReadUseCase.execute(new DefaultSubscriber<BooleanResponse>() {
            @Override
            public void onNext(BooleanResponse response) {
              Toast.makeText(getView().getContext(), "Response : " + response.success, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
              Toast.makeText(getView().getContext(), "Error : " + e, Toast.LENGTH_LONG).show();
            }
          }, unReadMessagesIds.unReadMessagesIds);
        }
      });
    }
  }

  private void subscribeForMessageStream() {
    getRoomMessageStreamUseCase.execute(new DefaultSubscriber<Message>() {
      @Override
      public void onNext(Message message) {
        super.onNext(message);

        MessageModel receivedMessage = messageModelMapper.transform(message);

        if (!messages.contains(receivedMessage)) {
          messages.add(receivedMessage);
          getView().showMessages(messages);
        }
      }

      @Override
      public void onError(Throwable e) {
        super.onError(e);
        subscribeForMessageStream();
      }
    });
  }

  private void loadMessages() {
    getRoomMessagesUseCase.execute(new LoggingSubscriber<List<Message>>() {
      @Override
      public void onNext(List<Message> input) {
        super.onNext(input);
        messages = new ArrayList<>(messageModelMapper.transform(input));
        getView().showMessages(messages);
      }

      @Override
      public void onError(Throwable e) {
        super.onError(e);
      }
    }, null);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    getRoomMessagesUseCase.unsubscribe();
    sendMessageUseCase.unsubscribe();
    getRoomMessageStreamUseCase.unsubscribe();
    getRoomUnreadMessagesUseCase.unsubscribe();
    markRoomMessagesReadUseCase.unsubscribe();
  }

  public void sendMessage(String text) {
    if (TextUtils.isEmpty(text)) {
      getView().showMessageFieldError("Please type message");
    } else {
      getView().onMessageSending();
      sendMessageUseCase.execute(new LoggingSubscriber<Message>() {
        @Override
        public void onError(Throwable e) {
          super.onError(e);
          Toast.makeText(activity, "Failed: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
          getView().onMessageSendingFailed();
        }

        @Override
        public void onNext(Message message) {
          super.onNext(message);
          messages.add(messageModelMapper.transform(message));
          getView().showMessages(messages);

          getView().onMessageSend();
        }
      }, text);
    }
  }

  public void loadMore(MessageModel first) {
    GetMessagesConfig config = new GetMessagesConfig(null, first.id, null, null);

    getRoomMessagesUseCase.execute(new DefaultSubscriber<List<Message>>() {
      @Override
      public void onNext(List<Message> oldMessages) {
        messages.addAll(0, messageModelMapper.transform(oldMessages));
        getView().showMoreMessages(messages, oldMessages.size() + 2);
      }
    }, config);
  }
}