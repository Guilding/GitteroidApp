package com.amatkivskiy.gitteroid.domain.interactor.user;

import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.interactor.base.AbstractUseCase;
import com.amatkivskiy.gitteroid.domain.repository.CloudUserRepository;
import com.amatkivskiy.gitteroid.domain.repository.UserAccountRepository;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

public class GetUserInfoFromInternetUseCase extends
                                            AbstractUseCase<Boolean, String> {

  private UserAccountRepository useUserAccountRepository;
  private CloudUserRepository cloudUserRepository;

  public GetUserInfoFromInternetUseCase(Scheduler threadExecutor,
                                        Scheduler postExecutionThread,
                                        UserAccountRepository useUserAccountRepository,
                                        CloudUserRepository cloudUserRepository) {
    super(threadExecutor, postExecutionThread);
    this.useUserAccountRepository = useUserAccountRepository;
    this.cloudUserRepository = cloudUserRepository;
  }

  @Override
  protected Observable<Boolean> buildUseCaseObservable(String code) {
    return this.cloudUserRepository.retrieveUserInfo(code).flatMap(
        new Func1<UserAccount, Observable<Boolean>>() {
          @Override
          public Observable<Boolean> call(UserAccount account) {
            return useUserAccountRepository.saveAccount(account);
          }
        });
  }
}
