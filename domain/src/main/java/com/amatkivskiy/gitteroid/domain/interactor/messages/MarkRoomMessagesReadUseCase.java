package com.amatkivskiy.gitteroid.domain.interactor.messages;

import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.interactor.base.AbstractUseCase;
import com.amatkivskiy.gitteroid.domain.repository.MessageRepository;
import com.amatkivskiy.gitter.sdk.model.response.BooleanResponse;

import java.util.List;

import rx.Observable;
import rx.Scheduler;

public class MarkRoomMessagesReadUseCase extends
                                         AbstractUseCase<BooleanResponse, List<String>> {

  private MessageRepository repository;
  private Room room;
  private UserAccount userAccount;

  public MarkRoomMessagesReadUseCase(Scheduler threadExecutor, Scheduler postExecutionThread,
                                     MessageRepository repository,
                                     Room room,
                                     UserAccount userAccount) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
    this.room = room;
    this.userAccount = userAccount;
  }

  @Override
  protected Observable<BooleanResponse> buildUseCaseObservable(List<String> ids) {
        return repository.markMessagesRead(room, userAccount.id, ids);
  }
}
