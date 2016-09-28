package com.amatkivskiy.gitteroid.di.module;

import android.support.annotation.Nullable;

import com.amatkivskiy.gitteroid.data.entity.mapper.UserInfoMapper;
import com.amatkivskiy.gitteroid.data.prefs.ComplexPreferences;
import com.amatkivskiy.gitteroid.data.repository.SharedPrefsUserAccountRepository;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.repository.UserAccountRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserAccountModule {

  @Provides
  @Singleton
  UserAccountRepository provideAccountRepository(ComplexPreferences preferences) {
    return new SharedPrefsUserAccountRepository(preferences);
  }

  @Nullable
  @Provides
  UserAccount provideUserAccount(UserAccountRepository repository) {
//    TODO: need to design something better solution then that
      return repository.getAccounts().toBlocking().first();
  }

  @Provides
  @Singleton
  UserInfoMapper provideUserInfoMapper() {
    return new UserInfoMapper();
  }
}
