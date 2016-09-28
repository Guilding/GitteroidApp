package com.amatkivskiy.gitteroid.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.model.RoomModel;
import com.amatkivskiy.gitteroid.presenter.RoomUsersActivityPresenter;
import com.amatkivskiy.gitteroid.ui.LoadingCallbacks;
import com.amatkivskiy.gitteroid.ui.activity.base.BackActivity;
import com.amatkivskiy.gitteroid.ui.adapter.UsersAdapter;
import com.amatkivskiy.gitteroid.domain.entity.User;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.layoutmanagers.ScrollSmoothLineaerLayoutManager;
import com.marshalchen.ultimaterecyclerview.ui.divideritemdecoration.HorizontalDividerItemDecoration;
import com.rey.material.widget.ProgressView;

import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

import java.util.List;

import butterknife.BindView;
import nucleus.factory.RequiresPresenter;

@IntentBuilder
@RequiresPresenter(RoomUsersActivityPresenter.class)
public class RoomUsersActivity extends BackActivity<RoomUsersActivityPresenter> implements LoadingCallbacks {

  @Extra
  RoomModel room;

  @BindView(R.id.recycler_view)
  UltimateRecyclerView userList;

  @BindView(R.id.progress_circular_view)
  ProgressView progressView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    RoomUsersActivityIntentBuilder.inject(getIntent(), this);

    setTitle(room.name);
  }

  public void showUsers(List<User> users) {
    onLoadingFinished();

    userList.setHasFixedSize(false);

    ScrollSmoothLineaerLayoutManager layoutManager =
        new ScrollSmoothLineaerLayoutManager(this,
                                             LinearLayoutManager.VERTICAL,
                                             false,
                                             300);
    userList.setLayoutManager(layoutManager);

    userList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.WHITE).build());

    UsersAdapter adapter = new UsersAdapter(this, users);
    userList.setAdapter(adapter);
  }

  @Override
  protected int getContentViewResource() {
    return R.layout.activity_room_users;
  }

  @Override
  public void onLoadingStarted() {
    userList.setVisibility(View.GONE);
    progressView.setVisibility(View.VISIBLE);
  }

  @Override
  public void onLoadingFinished() {
    userList.setVisibility(View.VISIBLE);
    progressView.setVisibility(View.GONE);
  }

  @Override
  public void onLoadingFailed() {
    userList.setVisibility(View.VISIBLE);
    progressView.setVisibility(View.GONE);
  }

  public RoomModel getRoom() {
    return room;
  }
}
