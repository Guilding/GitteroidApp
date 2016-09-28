package com.amatkivskiy.gitteroid.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.amatkivskiy.gitteroid.model.RoomModel;
import com.amatkivskiy.gitteroid.ui.view.ChatRoomView;
import com.amatkivskiy.gitteroid.ui.view.TabWithCloseButtonView;
import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

public class ViewChatRoomAdapter extends PagerAdapter implements PagerSlidingTabStrip.CustomTabProvider {
  public interface TabRemoveListener {
    void onTabRemoving(int position);
  }

  private TabRemoveListener removeListener;

  private Context context;
  private ArrayList<RoomModel> rooms;
  private ViewPager container;

  public ViewChatRoomAdapter(Context context, TabRemoveListener removeListener, ViewPager container) {
    this.removeListener = removeListener;
    this.context = context;
    this.container = container;
    this.rooms = new ArrayList<>();
  }

  public void addRoom(RoomModel room) {
    rooms.add(room);
    notifyDataSetChanged();
  }

  public void removeRoom(int position) {
    rooms.remove(position);
    destroyItem(container, position, container.getChildAt(position));

    notifyDataSetChanged();
  }

  public boolean isRoomAlreadyAdded(String id) {
    for (RoomModel room : rooms) {
      if (room.id.equals(id)) {
        return true;
      }
    }

    return false;
  }

  public RoomModel getRoom(int position) {
    return rooms.get(position);
  }

  public int getRoomPosition(String id) {
    for (RoomModel room : rooms) {
      if (room.id.equals(id)) {
        return rooms.indexOf(room);
      }
    }

    return -1;
  }

  @Override
  public int getCount() {
    return rooms.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    ChatRoomView roomView = new ChatRoomView(context);
    roomView.setRoom(rooms.get(position));

    container.addView(roomView);

    return roomView;
  }

  @Override
  public int getItemPosition(Object object) {
    ChatRoomView view = (ChatRoomView) object;
    if (rooms.contains(view.getRoom())) {
      return rooms.indexOf(view.getRoom());
    } else {
      return PagerAdapter.POSITION_NONE;
    }
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return rooms.get(position).name;
  }

  @Override
  public View getCustomTabView(ViewGroup viewGroup, final int i) {
    final TabWithCloseButtonView view = new TabWithCloseButtonView(context);

    view.setTitle(rooms.get(i).name);
    view.setCloseButtonOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        removeListener.onTabRemoving(i);
      }
    });

    view.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        view.switchCloseState(!view.isCloseStateEnabled());
        return false;
      }
    });

    return view;
  }

  @Override
  public void tabSelected(View view) {
  }

  @Override
  public void tabUnselected(View view) {
  }
}
