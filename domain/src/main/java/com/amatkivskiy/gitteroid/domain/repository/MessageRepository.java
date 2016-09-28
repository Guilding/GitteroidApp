package com.amatkivskiy.gitteroid.domain.repository;

import com.amatkivskiy.gitteroid.domain.entity.GetMessagesConfig;
import com.amatkivskiy.gitteroid.domain.entity.Message;
import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.entity.UnReadMessagesIds;
import com.amatkivskiy.gitter.sdk.model.response.BooleanResponse;

import java.util.List;

import rx.Observable;

public interface MessageRepository {
  Observable<List<Message>> getMessages(Room room, GetMessagesConfig config);

  Observable<Message> sendMessage(Room room, String text);

  Observable<UnReadMessagesIds> getUnReadMessagesIds(Room room, String userId);

  Observable<BooleanResponse> markMessagesRead(Room room, String userId, List<String> ids);
}
