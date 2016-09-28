package com.amatkivskiy.gitteroid.data.repository;

import com.amatkivskiy.gitteroid.data.entity.mapper.RoomsMapper;
import com.amatkivskiy.gitteroid.domain.entity.Room;
import com.amatkivskiy.gitteroid.domain.repository.UserRoomsRepository;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class NetworkUserRoomsRepository implements UserRoomsRepository {
  private RxGitterApiClient apiClient;
  private RoomsMapper roomsMapper;

  public NetworkUserRoomsRepository(RxGitterApiClient apiClient, RoomsMapper roomsMapper) {
    this.apiClient = apiClient;
    this.roomsMapper = roomsMapper;
  }

  @Override
  public Observable<List<Room>> getUserRooms(String userId) {
    return apiClient.getUserRooms(userId).map(new Func1<List<RoomResponse>, List<Room>>() {
      @Override
      public List<Room> call(List<RoomResponse> roomResponses) {
        return roomsMapper.transform(roomResponses);
      }
    });
  }
}
