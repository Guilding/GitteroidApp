package com.amatkivskiy.gitteroid.domain.entity;

public class Room {
  public final String id;
  public final String name;
  public final int unreadItems;
  public final String lastAccessTime;
  public final int mentions;

  public Room(String id, String name, int unreadItems, String lastAccessTime, int mentions) {
    this.id = id;
    this.name = name;
    this.unreadItems = unreadItems;
    this.lastAccessTime = lastAccessTime;
    this.mentions = mentions;
  }

  @Override
  public String toString() {
    return "Room{" +
           "id='" + id + '\'' +
           ", name='" + name + '\'' +
           ", unreadItems=" + unreadItems +
           ", lastAccessTime='" + lastAccessTime + '\'' +
           ", mentions=" + mentions +
           '}';
  }
}
