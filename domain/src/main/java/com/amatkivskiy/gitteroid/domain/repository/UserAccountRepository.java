package com.amatkivskiy.gitteroid.domain.repository;

import com.amatkivskiy.gitteroid.domain.entity.UserAccount;

import rx.Observable;

public interface UserAccountRepository {

  Observable<Boolean> saveAccount(UserAccount account);
  Observable<UserAccount> getAccounts();
  Observable<Boolean> deleteUser();
}
