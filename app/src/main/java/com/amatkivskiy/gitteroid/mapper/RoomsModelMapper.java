package com.amatkivskiy.gitteroid.mapper;

import com.amatkivskiy.gitteroid.model.RoomModel;
import com.amatkivskiy.gitteroid.data.entity.mapper.BaseMapper;
import com.amatkivskiy.gitteroid.domain.entity.Room;

public class RoomsModelMapper extends BaseMapper<Room, RoomModel> {

  private final static String AVATARS_BASE_URL = "https://avatars.githubusercontent.com/";

  public RoomModel transform(Room room) {
    return new RoomModel(room.id,
                         getShortName(room.name),
                         room.unreadItems,
                         room.mentions,
                         room.lastAccessTime,
                         createRoomIconUrl(room.name),
                         getGroupName(room.name),
                         room.name);
  }

  private static String createRoomIconUrl(String name) {
    String url;
    if (name.contains("/")) {
      url = AVATARS_BASE_URL + name.split("/")[0];
    } else {
      url = AVATARS_BASE_URL + name;
    }

    return url;
  }

  private static String getGroupName(String name) {
    String group;
    if (name.contains("/")) {
      group = name.split("/")[0];
    } else {
      group = name;
    }

    return group;
  }

  private static String getShortName(String name) {
    String shortName;
    if (name.contains("/")) {
      shortName = name.split("/")[1];
    } else {
      shortName = name;
    }

    return shortName;
  }
}
