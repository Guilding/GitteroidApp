package com.amatkivskiy.gitteroid.domain.interactor.base;

import rx.Subscriber;

public interface UseCaseArgumentless<Result> {
  void execute(Subscriber<Result> useCaseSubscriber);
}