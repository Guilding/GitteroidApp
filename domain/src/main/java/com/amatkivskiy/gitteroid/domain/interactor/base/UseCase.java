package com.amatkivskiy.gitteroid.domain.interactor.base;

import rx.Subscriber;

public interface UseCase<Result, Argument> {
  void execute(Subscriber<Result> UseCaseSubscriber, Argument arg);
}
