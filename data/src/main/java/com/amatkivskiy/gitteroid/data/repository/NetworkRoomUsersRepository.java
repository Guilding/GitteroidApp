package com.amatkivskiy.gitteroid.data.repository;

import com.amatkivskiy.gitteroid.data.entity.mapper.UserMapper;
import com.amatkivskiy.gitteroid.domain.entity.User;
import com.amatkivskiy.gitteroid.domain.repository.RoomUsersRepository;
import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class NetworkRoomUsersRepository implements RoomUsersRepository {

  private RxGitterApiClient apiClient;
  private UserMapper userMapper;

  public NetworkRoomUsersRepository(RxGitterApiClient apiClient,
                                    UserMapper userMapper) {
    this.apiClient = apiClient;
    this.userMapper = userMapper;
  }

  @Override
  public Observable<List<User>> getRoomUsers(String roomId) {
    return apiClient.getRoomUsers(roomId).map(new Func1<List<UserResponse>, List<User>>() {
      @Override
      public List<User> call(List<UserResponse> userResponses) {
        return userMapper.transform(userResponses);
      }
    });
  }
}
