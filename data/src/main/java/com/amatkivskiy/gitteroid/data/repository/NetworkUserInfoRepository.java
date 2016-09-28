package com.amatkivskiy.gitteroid.data.repository;

import com.amatkivskiy.gitteroid.data.entity.mapper.UserInfoMapper;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.repository.CloudUserRepository;
import com.amatkivskiy.gitter.sdk.model.response.AccessTokenResponse;
import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterAuthenticationClient;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class NetworkUserInfoRepository implements CloudUserRepository {

  private UserInfoMapper mapper;
  private RxGitterAuthenticationClient authenticateClient;

  public NetworkUserInfoRepository(UserInfoMapper mapper,
                                   RxGitterAuthenticationClient authenticateClient) {
    this.mapper = mapper;
    this.authenticateClient = authenticateClient;
  }

  @Override
  public Observable<UserAccount> retrieveUserInfo(String code) {
    return authenticateClient.getAccessToken(code).flatMap(
        new Func1<AccessTokenResponse, Observable<UserResponse>>() {
          @Override
          public Observable<UserResponse> call(AccessTokenResponse accessTokenResponse) {
            RxGitterApiClient api = new RxGitterApiClient.Builder()
                .withAccountToken(accessTokenResponse.accessToken)
                .build();

            return api.getCurrentUser();
          }
        }, new Func2<AccessTokenResponse, UserResponse, UserAccount>() {
          @Override
          public UserAccount call(AccessTokenResponse token, UserResponse userInfo) {
            return mapper.transform(token, userInfo);
          }
        });
  }
}
