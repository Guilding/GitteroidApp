package com.amatkivskiy.gitteroid.domain.interactor.base;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class BaseUseCase<Result> {

  private final Scheduler threadExecutorScheduler;
  private final Scheduler postExecutionScheduler;

  private Subscription subscription = Subscriptions.empty();

  protected BaseUseCase(Scheduler threadExecutor, Scheduler postExecutionThread) {
    this.threadExecutorScheduler = threadExecutor;
    this.postExecutionScheduler = postExecutionThread;
  }

  protected void subscribe(Observable<Result> source, Subscriber<Result> useCaseSubscriber) {
    this.subscription = source.subscribeOn(this.threadExecutorScheduler)
        .observeOn(this.postExecutionScheduler)
        .subscribe(useCaseSubscriber);
  }

  public void unsubscribe() {
    if (!subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }
}
