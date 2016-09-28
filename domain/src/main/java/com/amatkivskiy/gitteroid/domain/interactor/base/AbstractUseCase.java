package com.amatkivskiy.gitteroid.domain.interactor.base;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

public abstract class AbstractUseCase<Result,Argument> extends BaseUseCase<Result> implements UseCase<Result,Argument>{

  protected AbstractUseCase(Scheduler threadExecutor, Scheduler postExecutionThread) {
    super(threadExecutor, postExecutionThread);
  }

  protected abstract Observable<Result> buildUseCaseObservable(Argument argument);

  @Override
  public void execute(Subscriber<Result> useCaseSubscriber, Argument argument) {
    this.subscribe(this.buildUseCaseObservable(argument), useCaseSubscriber);
  }
}
