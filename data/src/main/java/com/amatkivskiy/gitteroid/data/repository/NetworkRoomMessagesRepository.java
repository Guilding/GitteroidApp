package com.amatkivskiy.gitteroid.data.repository;

import com.amatkivskiy.gitteroid.data.entity.mapper.MessagesMapper;
import com.amatkivskiy.gitteroid.domain.entity.GetMessagesConfig;
import com.amatkivskiy.gitteroid.domain.entity.Message;
import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.entity.UnReadMessagesIds;
import com.amatkivskiy.gitteroid.domain.repository.MessageRepository;
import com.amatkivskiy.gitter.sdk.model.request.ChatMessagesRequestParams.ChatMessagesRequestParamsBuilder;
import com.amatkivskiy.gitter.sdk.model.response.BooleanResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.UnReadMessagesResponse;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class NetworkRoomMessagesRepository implements MessageRepository {

  private RxGitterApiClient apiClient;
  private MessagesMapper messagesMapper;

  private final Func1<List<MessageResponse>, List<Message>> listMessagesConverter =
      new Func1<List<MessageResponse>, List<Message>>() {
        @Override
        public List<Message> call(List<MessageResponse> messageResponses) {
          return messagesMapper.transform(messageResponses);
        }
      };

  private final Func1<MessageResponse, Message> messageConverter =
      new Func1<MessageResponse, Message>() {
        @Override
        public Message call(MessageResponse messageResponses) {
          return messagesMapper.transform(messageResponses);
        }
      };

  public NetworkRoomMessagesRepository(RxGitterApiClient apiClient,
                                       MessagesMapper messagesMapper) {
    this.apiClient = apiClient;
    this.messagesMapper = messagesMapper;
  }

  @Override
  public Observable<List<Message>> getMessages(Room room, GetMessagesConfig config) {
    if (config != null) {
//      Need to fix in next version.
      ChatMessagesRequestParamsBuilder params = new ChatMessagesRequestParamsBuilder()
          .afterId(config.afterId)
          .beforeId(config.beforeId);

      if (config.limit != null) {
        params.limit(config.limit);
      }

      if (config.skipCount != null) {
        params.skip(config.skipCount);
      }

      return apiClient.getRoomMessages(room.id, params.build()).map(listMessagesConverter);
    } else {
      return apiClient.getRoomMessages(room.id).map(listMessagesConverter);
    }
  }

  @Override
  public Observable<Message> sendMessage(Room room, String text) {
    return apiClient.sendMessage(room.id, text).map(messageConverter);
  }

  @Override
  public Observable<UnReadMessagesIds> getUnReadMessagesIds(Room room, String userId) {
    return apiClient.getUnReadMessages(userId, room.id).map(new Func1<UnReadMessagesResponse, UnReadMessagesIds>() {
      @Override
      public UnReadMessagesIds call(UnReadMessagesResponse unReadMessagesResponse) {
        return new UnReadMessagesIds(unReadMessagesResponse.unReadMessagesIds,
                                     unReadMessagesResponse.unReadMentionedMessagesIds);
      }
    });
  }

  @Override
  public Observable<BooleanResponse> markMessagesRead(Room room, String userId, List<String> ids) {
    return apiClient.markReadMessages(userId, room.id, ids);
  }
}
