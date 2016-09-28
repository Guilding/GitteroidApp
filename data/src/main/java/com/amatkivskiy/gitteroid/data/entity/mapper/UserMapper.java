package com.amatkivskiy.gitteroid.data.entity.mapper;

import com.amatkivskiy.gitteroid.domain.entity.User;
import com.amatkivskiy.gitter.sdk.model.response.UserResponse;

public class UserMapper extends BaseMapper<UserResponse, User> {

  @Override
  public User transform(UserResponse input) {
    return new User(
        input.id,
        input.v,
        input.username,
        input.avatarUrlSmall,
        input.gv,
        input.displayName,
        input.url,
        input.avatarUrlMedium
    );
  }
}
