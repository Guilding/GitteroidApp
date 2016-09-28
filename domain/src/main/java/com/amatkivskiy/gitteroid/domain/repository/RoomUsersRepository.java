package com.amatkivskiy.gitteroid.domain.repository;

import com.amatkivskiy.gitteroid.domain.entity.User;

import java.util.List;

import rx.Observable;

public interface RoomUsersRepository {
  Observable<List<User>> getRoomUsers(String roomId);
}
