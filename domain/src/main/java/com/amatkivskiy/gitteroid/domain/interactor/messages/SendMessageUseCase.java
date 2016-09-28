package com.amatkivskiy.gitteroid.domain.interactor.messages;

import com.amatkivskiy.gitteroid.domain.entity.Message;
import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.interactor.base.AbstractUseCase;
import com.amatkivskiy.gitteroid.domain.repository.MessageRepository;

import rx.Observable;
import rx.Scheduler;

public class SendMessageUseCase extends AbstractUseCase<Message, String> {
  private MessageRepository repository;
  private Room room;

  public SendMessageUseCase(Scheduler threadExecutor,
                            Scheduler postExecutionThread,
                            MessageRepository repository,
                            Room room) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
    this.room = room;
  }

  @Override
  protected Observable<Message> buildUseCaseObservable(String text) {
    return repository.sendMessage(room, text);
  }
}