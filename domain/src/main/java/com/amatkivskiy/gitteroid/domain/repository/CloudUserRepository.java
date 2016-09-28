package com.amatkivskiy.gitteroid.domain.repository;

import com.amatkivskiy.gitteroid.domain.entity.UserAccount;

import rx.Observable;

public interface CloudUserRepository {
  Observable<UserAccount> retrieveUserInfo(String code);
}
