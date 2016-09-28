package com.amatkivskiy.gitteroid.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amatkivskiy.gitteroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.text_view_author_name)
  public TextView author;
  @BindView(R.id.text_view_message_date)
  public TextView date;
  @BindView(R.id.text_view_message_body)
  public TextView text;
  @BindView(R.id.image_view_message_author_icon)
  public ImageView icon;

  public MessageViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}