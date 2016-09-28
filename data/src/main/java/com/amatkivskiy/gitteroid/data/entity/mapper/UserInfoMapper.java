package com.amatkivskiy.gitteroid.data.entity.mapper;

import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitter.sdk.model.response.AccessTokenResponse;
import com.amatkivskiy.gitter.sdk.model.response.UserResponse;

public class UserInfoMapper {

  public UserAccount transform(AccessTokenResponse accessTokenResponse,
                               UserResponse userInfoResponse) {
    return new UserAccount(accessTokenResponse.accessToken,
        userInfoResponse.username,
        userInfoResponse.id,
        userInfoResponse.displayName,
        userInfoResponse.avatarUrlMedium);
  }

  public UserAccount transform(UserResponse userInfoResponse) {
    return new UserAccount(null,
        userInfoResponse.username,
        userInfoResponse.id,
        userInfoResponse.displayName,
        userInfoResponse.avatarUrlMedium);
  }

}
