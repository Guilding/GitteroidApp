package com.amatkivskiy.gitteroid.data.entity.mapper;

import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;

public class RoomsMapper extends BaseMapper<RoomResponse, Room> {

  public Room transform(RoomResponse room) {
    return new Room(room.id, room.name, room.unreadItems, room.lastAccessTime, room.mentions);
  }
}
