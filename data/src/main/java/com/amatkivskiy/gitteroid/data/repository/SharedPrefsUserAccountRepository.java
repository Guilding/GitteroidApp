package com.amatkivskiy.gitteroid.data.repository;

import com.amatkivskiy.gitteroid.data.prefs.ComplexPreferences;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.repository.UserAccountRepository;

import rx.Observable;
import rx.Subscriber;

public class SharedPrefsUserAccountRepository implements UserAccountRepository {

  private ComplexPreferences prefs;

  public SharedPrefsUserAccountRepository(ComplexPreferences preferences) {
    prefs = preferences;
  }

  @Override
  public Observable<Boolean> saveAccount(final UserAccount account) {
    return Observable.create(new Observable.OnSubscribe<Boolean>() {
      @Override
      public void call(Subscriber<? super Boolean> subscriber) {
        prefs.saveAccount(account);
        subscriber.onCompleted();
      }
    });
  }

  @Override
  public Observable<UserAccount> getAccounts() {
    return Observable.create(new Observable.OnSubscribe<UserAccount>() {
      @Override
      public void call(Subscriber<? super UserAccount> subscriber) {
        subscriber.onNext(prefs.getAccount());
        subscriber.onCompleted();
      }
    });
  }

  @Override
  public Observable<Boolean> deleteUser() {
    return Observable.create(new Observable.OnSubscribe<Boolean>() {
      @Override
      public void call(Subscriber<? super Boolean> subscriber) {
        prefs.saveAccount(null);
        subscriber.onCompleted();
      }
    });
  }
}
