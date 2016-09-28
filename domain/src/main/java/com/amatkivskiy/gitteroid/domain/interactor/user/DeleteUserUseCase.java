package com.amatkivskiy.gitteroid.domain.interactor.user;

import com.amatkivskiy.gitteroid.domain.interactor.base.AbstractUseCaseArgumentless;
import com.amatkivskiy.gitteroid.domain.repository.UserAccountRepository;

import rx.Observable;
import rx.Scheduler;

public class DeleteUserUseCase extends AbstractUseCaseArgumentless<Boolean>{
  private UserAccountRepository repository;

  public DeleteUserUseCase(Scheduler threadExecutor, Scheduler postExecutionThread,
                           UserAccountRepository repository) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }

  @Override
  protected Observable<Boolean> buildUseCaseObservable() {
    return repository.deleteUser();
  }
}
