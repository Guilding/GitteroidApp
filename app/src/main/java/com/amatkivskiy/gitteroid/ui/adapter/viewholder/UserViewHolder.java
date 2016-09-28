package com.amatkivskiy.gitteroid.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amatkivskiy.gitteroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.user_name)
  public TextView userName;
  @BindView(R.id.iv_avatar)
  public ImageView avatar;

  public UserViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
