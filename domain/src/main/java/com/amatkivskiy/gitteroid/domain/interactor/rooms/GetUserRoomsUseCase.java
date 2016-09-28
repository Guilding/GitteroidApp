package com.amatkivskiy.gitteroid.domain.interactor.rooms;

import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.interactor.base.AbstractUseCase;
import com.amatkivskiy.gitteroid.domain.repository.UserRoomsRepository;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

public class GetUserRoomsUseCase extends AbstractUseCase<List<Room>, String> {
  private UserRoomsRepository repository;
  private Func1<List<Room>, List<Room>> sortFunc;

  public GetUserRoomsUseCase(Scheduler threadExecutor, Scheduler postExecutionThread,
                             UserRoomsRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }

  public void sortBy(Func1<List<Room>, List<Room>> howToSort) {
    this.sortFunc = howToSort;
  }

  @Override
  protected Observable<List<Room>> buildUseCaseObservable(String id) {
    Observable<List<Room>> observable = repository.getUserRooms(id);

    if (sortFunc != null) {
      return observable.map(sortFunc);
    }

    return observable;
  }
}
