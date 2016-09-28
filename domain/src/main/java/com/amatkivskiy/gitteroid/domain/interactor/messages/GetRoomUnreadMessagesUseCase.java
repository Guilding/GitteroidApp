package com.amatkivskiy.gitteroid.domain.interactor.messages;

import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.entity.UnReadMessagesIds;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.interactor.base.AbstractUseCaseArgumentless;
import com.amatkivskiy.gitteroid.domain.repository.MessageRepository;

import rx.Observable;
import rx.Scheduler;

public class GetRoomUnreadMessagesUseCase extends
                                          AbstractUseCaseArgumentless<UnReadMessagesIds> {

  private MessageRepository repository;
  private Room room;
  private UserAccount userAccount;

  public GetRoomUnreadMessagesUseCase(Scheduler threadExecutor, Scheduler postExecutionThread,
                                      MessageRepository repository,
                                      Room room,
                                      UserAccount userAccount) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
    this.room = room;
    this.userAccount = userAccount;
  }

  @Override
  protected Observable<UnReadMessagesIds> buildUseCaseObservable() {
    return repository.getUnReadMessagesIds(room, userAccount.id);
  }
}