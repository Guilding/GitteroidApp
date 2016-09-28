package com.amatkivskiy.gitteroid.domain.entity;

public class UserAccount {
  public final String token;
  public final String username;
  public final String id;
  public final String displayName;
  public final String avatarUrlMedium;

  public UserAccount(String token, String username, String id, String displayName,
                     String avatarUrlMedium) {
    this.token = token;
    this.username = username;
    this.id = id;
    this.displayName = displayName;
    this.avatarUrlMedium = avatarUrlMedium;
  }

  @Override
  public String toString() {
    return "UserAccount{" +
           "token='" + token + '\'' +
           ", username='" + username + '\'' +
           ", id='" + id + '\'' +
           ", displayName='" + displayName + '\'' +
           ", avatarUrlMedium='" + avatarUrlMedium + '\'' +
           '}';
  }
}
