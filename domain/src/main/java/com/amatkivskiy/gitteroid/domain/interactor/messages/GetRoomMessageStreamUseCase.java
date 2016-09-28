package com.amatkivskiy.gitteroid.domain.interactor.messages;

import com.amatkivskiy.gitteroid.domain.entity.Message;
import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.interactor.base.AbstractUseCaseArgumentless;
import com.amatkivskiy.gitteroid.domain.repository.StreamingMessageRepository;

import rx.Observable;
import rx.Scheduler;

public class GetRoomMessageStreamUseCase extends AbstractUseCaseArgumentless<Message> {

  private StreamingMessageRepository repository;
  private Room room;

  public GetRoomMessageStreamUseCase(Scheduler threadExecutor,
                                     Scheduler postExecutionThread,
                                     StreamingMessageRepository repository,
                                     Room room) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
    this.room = room;
  }

  @Override
  protected Observable<Message> buildUseCaseObservable() {
    return repository.getRoomMessageStream(room);
  }
}
