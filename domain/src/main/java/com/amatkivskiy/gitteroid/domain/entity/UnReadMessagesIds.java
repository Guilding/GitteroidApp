package com.amatkivskiy.gitteroid.domain.entity;

import java.util.List;

public class UnReadMessagesIds {
  public final List<String> unReadMessagesIds;
  public final List<String> unReadMentionedMessagesIds;

  public UnReadMessagesIds(List<String> unReadMessagesIds, List<String> unReadMentionedMessagesIds) {
    this.unReadMessagesIds = unReadMessagesIds;
    this.unReadMentionedMessagesIds = unReadMentionedMessagesIds;
  }
}
