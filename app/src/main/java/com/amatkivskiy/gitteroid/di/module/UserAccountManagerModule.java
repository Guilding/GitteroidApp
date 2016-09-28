package com.amatkivskiy.gitteroid.di.module;

import com.amatkivskiy.gitteroid.di.internals.BackgroundScheduler;
import com.amatkivskiy.gitteroid.di.internals.MainThreadScheduler;
import com.amatkivskiy.gitteroid.data.entity.mapper.UserInfoMapper;
import com.amatkivskiy.gitteroid.data.repository.NetworkUserInfoRepository;
import com.amatkivskiy.gitteroid.domain.interactor.user.DeleteUserUseCase;
import com.amatkivskiy.gitteroid.domain.interactor.user.GetUserInfoFromInternetUseCase;
import com.amatkivskiy.gitteroid.domain.repository.CloudUserRepository;
import com.amatkivskiy.gitteroid.domain.repository.UserAccountRepository;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterAuthenticationClient;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class UserAccountManagerModule {

  @Provides
  GetUserInfoFromInternetUseCase provideSaveAccountUserCase(
      @BackgroundScheduler Scheduler backgroundScheduler,
      @MainThreadScheduler Scheduler mainScheduler,
      UserAccountRepository repository,
      CloudUserRepository networkRepository) {
    return new GetUserInfoFromInternetUseCase(backgroundScheduler, mainScheduler, repository,
                                              networkRepository);
  }

  @Provides
  DeleteUserUseCase provideDeleteUserUseCase(
      @BackgroundScheduler Scheduler backgroundScheduler,
      @MainThreadScheduler Scheduler mainScheduler,
      UserAccountRepository repository) {
    return new DeleteUserUseCase(backgroundScheduler, mainScheduler, repository);
  }

  @Provides
  CloudUserRepository provideNetworkRepository(UserInfoMapper userInfoMapper) {
    return new NetworkUserInfoRepository(userInfoMapper, new RxGitterAuthenticationClient.Builder().build());
  }
}
