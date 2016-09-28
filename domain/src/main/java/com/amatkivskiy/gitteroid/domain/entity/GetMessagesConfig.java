package com.amatkivskiy.gitteroid.domain.entity;

public class GetMessagesConfig {

  public final Integer skipCount;
  public final String beforeId;
  public final String afterId;
  public final Integer limit;

  public GetMessagesConfig(Integer skipCount, String beforeId, String afterId, Integer limit) {
    this.skipCount = skipCount;
    this.beforeId = beforeId;
    this.afterId = afterId;
    this.limit = limit;
  }
}
