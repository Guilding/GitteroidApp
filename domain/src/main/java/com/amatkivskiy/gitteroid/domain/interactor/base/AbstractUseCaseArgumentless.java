package com.amatkivskiy.gitteroid.domain.interactor.base;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

public abstract class AbstractUseCaseArgumentless<Result> extends BaseUseCase<Result>
    implements UseCaseArgumentless<Result> {

  protected AbstractUseCaseArgumentless(Scheduler threadExecutor, Scheduler postExecutionThread) {
    super(threadExecutor, postExecutionThread);
  }

  protected abstract Observable<Result> buildUseCaseObservable();

  @Override
  public void execute(Subscriber<Result> useCaseSubscriber) {
    this.subscribe(this.buildUseCaseObservable(), useCaseSubscriber);
  }
}
