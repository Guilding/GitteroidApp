package com.amatkivskiy.gitteroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.ui.adapter.viewholder.MessageViewHolder;
import com.amatkivskiy.gitteroid.ui.adapter.viewholder.UserViewHolder;
import com.amatkivskiy.gitteroid.util.CropCircleTransformation;
import com.amatkivskiy.gitteroid.domain.entity.User;
import com.bumptech.glide.Glide;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

public class UsersAdapter extends UltimateViewAdapter<UserViewHolder> {

  private Context context;
  private List<User> users;

  public UsersAdapter(Context context, List<User> users) {
    this.context = context;
    this.users = users;
  }

  @Override
  public void onBindViewHolder(UserViewHolder holder, int position) {
    if (position < getItemCount()) {
      User user = users.get(position);

      holder.userName.setText(user.username);

      Glide.with(context)
          .load(user.avatarUrlMedium)
          .bitmapTransform(new CropCircleTransformation(context))
          .into(holder.avatar);
    }
  }

  @Override
  public int getAdapterItemCount() {
    return users.size();
  }

  @Override
  public long generateHeaderId(int i) {
    return 0;
  }

  @Override
  public int getItemViewType(int position) {
    return super.getItemViewType(position);
  }

  @Override
  public UserViewHolder newCustomViewHolder(View view) {
    return new UserViewHolder(view);
  }

  @Override
  public UserViewHolder newFooterHolder(View view) {
    return null;
  }

  @Override
  public UserViewHolder newHeaderHolder(View view) {
    return null;
  }

  @Override
  public UserViewHolder onCreateViewHolder(ViewGroup parent) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
    return new UserViewHolder(v);
  }

  @Override
  public MessageViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
    return null;
  }

  @Override
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
  }
}
