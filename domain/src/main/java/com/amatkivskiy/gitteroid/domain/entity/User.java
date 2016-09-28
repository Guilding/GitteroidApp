package com.amatkivskiy.gitteroid.domain.entity;

public class User {

  public final String id;
  public final Integer v;
  public final String username;
  public final String displayName;
  public final String avatarUrlSmall;
  public final String avatarUrlMedium;
  public final String gv;
  public final String url;

  public User(String id, Integer v, String username, String avatarUrlSmall, String gv, String displayName, String url,
              String avatarUrlMedium) {
    this.id = id;
    this.v = v;
    this.username = username;
    this.avatarUrlSmall = avatarUrlSmall;
    this.gv = gv;
    this.displayName = displayName;
    this.url = url;
    this.avatarUrlMedium = avatarUrlMedium;
  }

  @Override
  public String toString() {
    return "User{" +
           "username='" + username + '\'' +
           '}';
  }
}
