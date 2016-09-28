package com.amatkivskiy.gitteroid.data.repository;

import com.amatkivskiy.gitteroid.data.entity.mapper.MessagesMapper;
import com.amatkivskiy.gitteroid.domain.entity.Message;
import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.repository.StreamingMessageRepository;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterStreamingApiClient;

import rx.Observable;
import rx.functions.Func1;

public class NetworkStreamingMessageRepository implements StreamingMessageRepository {

  private RxGitterStreamingApiClient apiClient;
  private MessagesMapper messagesMapper;

  private final Func1<MessageResponse, Message> messageConverter =
      new Func1<MessageResponse, Message>() {
        @Override
        public Message call(MessageResponse messageResponses) {
          return messagesMapper.transform(messageResponses);
        }
      };

  public NetworkStreamingMessageRepository(RxGitterStreamingApiClient apiClient,
                                           MessagesMapper messagesMapper) {
    this.apiClient = apiClient;
    this.messagesMapper = messagesMapper;
  }

  @Override
  public Observable<Message> getRoomMessageStream(Room room) {
    return this.apiClient.getRoomMessagesStream(room.id).map(messageConverter);
  }
}
