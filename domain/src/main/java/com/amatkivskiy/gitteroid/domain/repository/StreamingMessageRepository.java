package com.amatkivskiy.gitteroid.domain.repository;

import com.amatkivskiy.gitteroid.domain.entity.Message;
import com.amatkivskiy.gitteroid.domain.entity.Room;

import rx.Observable;

public interface StreamingMessageRepository {

  Observable<Message> getRoomMessageStream(Room room);
}
