package com.amatkivskiy.gitteroid.domain.interactor.user;

import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.interactor.base.AbstractUseCaseArgumentless;
import com.amatkivskiy.gitteroid.domain.repository.UserAccountRepository;

import rx.Observable;
import rx.Scheduler;

public class GetUserAccountUseCase extends
                                    AbstractUseCaseArgumentless<UserAccount> {

  private UserAccountRepository usUserAccountRepository;

  public GetUserAccountUseCase(Scheduler threadExecutor, Scheduler postExecutionThread,
                               UserAccountRepository usUserAccountRepository) {
    super(threadExecutor, postExecutionThread);
    this.usUserAccountRepository = usUserAccountRepository;
  }

  @Override
  protected Observable<UserAccount> buildUseCaseObservable() {
    return this.usUserAccountRepository.getAccounts();
  }
}
