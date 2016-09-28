package com.amatkivskiy.gitteroid.data.entity.mapper;

import com.amatkivskiy.gitteroid.domain.entity.Message;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;


public class MessagesMapper extends BaseMapper<MessageResponse, Message> {

  private UserInfoMapper userInfoMapper;

  public MessagesMapper(UserInfoMapper userInfoMapper) {
    this.userInfoMapper = userInfoMapper;
  }

  @Override
  public Message transform(MessageResponse input) {
    return new Message(input.id,
                         input.text,
                         input.html,
                         input.sent,
                         input.editedAt,
                         userInfoMapper.transform(input.fromUser),
                         input.unRead,
                         input.readBy,
                         input.version);
  }
}
