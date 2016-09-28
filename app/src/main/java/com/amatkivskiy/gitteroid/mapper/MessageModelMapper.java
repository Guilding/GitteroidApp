package com.amatkivskiy.gitteroid.mapper;

import com.amatkivskiy.gitteroid.model.MessageModel;
import com.amatkivskiy.gitteroid.model.UserAccountModel;
import com.amatkivskiy.gitteroid.data.entity.mapper.BaseMapper;
import com.amatkivskiy.gitteroid.domain.entity.Message;

public class MessageModelMapper extends BaseMapper<Message, MessageModel> {

  @Override
  public MessageModel transform(Message input) {
    UserAccountModel fromUser = new UserAccountModelMapper().transform(input.fromUser);
    return new MessageModel(input.id,
                            input.text,
                            input.html,
                            input.sent,
                            input.editedAt,
                            fromUser,
                            input.unRead,
                            input.readBy,
                            input.version);
  }
}
