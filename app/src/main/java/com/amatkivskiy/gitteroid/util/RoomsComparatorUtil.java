package com.amatkivskiy.gitteroid.util;

import com.amatkivskiy.gitteroid.domain.entity.Room;

import java.util.Comparator;

public class RoomsComparatorUtil {

  /**
   * Returns comparator that sorts with the following order: <ul>1. Rooms with biggest
   * <b>Room.mentions</b>.</ul> <ul>2. Rooms with biggest unread items <b>Room.unreadItems</b>.</ul>
   * <ul>3. Other rooms that have <b>Room.lastAccessTime</b>.</ul> <ul>4. Rooms that don't have
   * <b>Room.lastAccessTime</b></ul>
   */
  public static Comparator<Room> getComplexRoomComparator() {
    return new Comparator<Room>() {
      @Override
      public int compare(Room room1, Room room2) {
        if (room1.mentions != 0 && room2.mentions != 0) {
          return Integer.valueOf(room1.mentions).compareTo(room2.mentions);
        }

        if (room1.mentions != 0 && room2.mentions == 0) {
          return -1;
        }

        if (room1.mentions == 0 && room2.mentions != 0) {
          return 1;
        }

        if (room1.unreadItems != 0 && room2.unreadItems != 0) {
          return Integer.valueOf(room2.unreadItems).compareTo(room1.unreadItems);
        }

        if (room1.unreadItems != 0 && room2.unreadItems == 0) {
          return -1;
        }

        if (room1.unreadItems == 0 && room2.unreadItems != 0) {
          return 1;
        }

        if (room1.lastAccessTime != null && room2.lastAccessTime == null) {
          return -1;
        }

        if (room1.lastAccessTime == null && room2.lastAccessTime != null) {
          return 1;
        }

        return 0;
      }
    };
  }
}
