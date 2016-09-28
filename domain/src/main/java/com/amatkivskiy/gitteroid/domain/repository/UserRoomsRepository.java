package com.amatkivskiy.gitteroid.domain.repository;

import com.amatkivskiy.gitteroid.domain.entity.Room;

import java.util.List;

import rx.Observable;

public interface UserRoomsRepository {
  Observable<List<Room>> getUserRooms(String userId);
}
