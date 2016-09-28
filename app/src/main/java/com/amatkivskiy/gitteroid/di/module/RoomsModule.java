package com.amatkivskiy.gitteroid.di.module;

import android.support.annotation.Nullable;

import com.amatkivskiy.gitteroid.di.internals.BackgroundScheduler;
import com.amatkivskiy.gitteroid.di.internals.MainThreadScheduler;
import com.amatkivskiy.gitteroid.mapper.RoomsModelMapper;
import com.amatkivskiy.gitteroid.data.entity.mapper.RoomsMapper;
import com.amatkivskiy.gitteroid.data.repository.NetworkUserRoomsRepository;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.interactor.rooms.GetUserRoomsUseCase;
import com.amatkivskiy.gitteroid.domain.repository.UserRoomsRepository;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class RoomsModule {

  @Provides
  UserRoomsRepository provideUserRoomsRepository(@Nullable UserAccount account) {
    return new NetworkUserRoomsRepository(new RxGitterApiClient.Builder()
                                              .withAccountToken(account.token)
                                              .build(),
                                          new RoomsMapper());
  }

  @Provides
  GetUserRoomsUseCase provideUserRoomsUseCase(
      @BackgroundScheduler Scheduler backgroundScheduler,
      @MainThreadScheduler Scheduler mainScheduler,
      UserRoomsRepository repository) {
    return new GetUserRoomsUseCase(backgroundScheduler, mainScheduler, repository);
  }

  @Provides
  RoomsModelMapper provideRoomsModelMapper() {
    return new RoomsModelMapper();
  }
}
