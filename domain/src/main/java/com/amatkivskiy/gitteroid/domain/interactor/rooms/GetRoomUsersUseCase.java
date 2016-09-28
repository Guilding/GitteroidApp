package com.amatkivskiy.gitteroid.domain.interactor.rooms;

import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.entity.User;
import com.amatkivskiy.gitteroid.domain.interactor.base.AbstractUseCaseArgumentless;
import com.amatkivskiy.gitteroid.domain.repository.RoomUsersRepository;

import java.util.List;

import rx.Observable;
import rx.Scheduler;

public class GetRoomUsersUseCase extends AbstractUseCaseArgumentless<List<User>> {

  private Room room;
  private RoomUsersRepository repository;

  public GetRoomUsersUseCase(Scheduler threadExecutor,
                             Scheduler postExecutionThread,
                             RoomUsersRepository repository,
                             Room room) {
    super(threadExecutor, postExecutionThread);
    this.room = room;
    this.repository = repository;
  }

  @Override
  protected Observable<List<User>> buildUseCaseObservable() {
    return repository.getRoomUsers(room.id);
  }
}