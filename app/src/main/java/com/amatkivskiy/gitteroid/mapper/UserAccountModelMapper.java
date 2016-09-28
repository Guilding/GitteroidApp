package com.amatkivskiy.gitteroid.mapper;

import com.amatkivskiy.gitteroid.model.UserAccountModel;
import com.amatkivskiy.gitteroid.data.entity.mapper.BaseMapper;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;

public class UserAccountModelMapper extends BaseMapper<UserAccount, UserAccountModel> {

  @Override
  public UserAccountModel transform(UserAccount input) {
    return new UserAccountModel(input.token,
                                input.username,
                                input.id,
                                input.displayName,
                                input.avatarUrlMedium);
  }
}
