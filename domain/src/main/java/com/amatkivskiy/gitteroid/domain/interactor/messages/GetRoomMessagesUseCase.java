package com.amatkivskiy.gitteroid.domain.interactor.messages;

import com.amatkivskiy.gitteroid.domain.entity.GetMessagesConfig;
import com.amatkivskiy.gitteroid.domain.entity.Message;
import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.interactor.base.AbstractUseCase;
import com.amatkivskiy.gitteroid.domain.repository.MessageRepository;

import java.util.List;

import rx.Observable;
import rx.Scheduler;

public class GetRoomMessagesUseCase extends AbstractUseCase<List<Message>, GetMessagesConfig> {
  private MessageRepository repository;
  private Room room;

  public GetRoomMessagesUseCase(Scheduler threadExecutor, Scheduler postExecutionThread,
                                MessageRepository repository,
                                Room room) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
    this.room = room;
  }

  @Override
  protected Observable<List<Message>> buildUseCaseObservable(GetMessagesConfig getMessagesConfig) {
    return repository.getMessages(room, getMessagesConfig);
  }
}
