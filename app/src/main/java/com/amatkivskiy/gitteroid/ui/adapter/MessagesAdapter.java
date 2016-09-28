package com.amatkivskiy.gitteroid.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.model.MessageModel;
import com.amatkivskiy.gitteroid.ui.adapter.viewholder.MessageViewHolder;
import com.amatkivskiy.gitteroid.util.CropCircleTransformation;
import com.bumptech.glide.Glide;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import net.danlew.android.joda.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;

public class MessagesAdapter extends UltimateViewAdapter<MessageViewHolder> {

  private Context context;
  private List<MessageModel> messages;

  public MessagesAdapter(Context context, List<MessageModel> messages) {
    this.context = context;
    this.messages = messages;
  }

  public MessageModel getFirstMessage() {
    return messages.get(0);
  }

  public void updateMessages(List<MessageModel> newOne) {
    this.messages.clear();
    this.messages.addAll(newOne);
    notifyDataSetChanged();
  }

  @Override
  public void onBindViewHolder(MessageViewHolder holder, int position) {
    if (position < getItemCount()) {
      MessageModel message = messages.get(position);

      holder.text.setMovementMethod(LinkMovementMethod.getInstance());
      if (TextUtils.isEmpty(message.html)) {
        holder.text.setText("This message was deleted");
        holder.text.setTextColor(Color.GRAY);
      } else {
        holder.text.setText(Html.fromHtml(message.html));
        holder.text.setTextColor(Color.BLACK);
      }

      holder.author.setText(message.fromUser.username);

      DateTime time = ISODateTimeFormat.dateTimeParser().parseDateTime(message.sent);
      holder.date.setText(DateUtils.getRelativeDateTimeString(context, time, null, 0));

      Glide.with(context)
          .load(message.fromUser.avatarUrlMedium)
          .bitmapTransform(new CropCircleTransformation(context))
          .into(holder.icon);
    }
  }

  @Override
  public int getAdapterItemCount() {
    return messages.size();
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
  public MessageViewHolder newCustomViewHolder(View view) {
    return new MessageViewHolder(view);
  }

  @Override
  public MessageViewHolder newFooterHolder(View view) {
    return null;
  }

  @Override
  public MessageViewHolder newHeaderHolder(View view) {
    return null;
  }

  @Override
  public MessageViewHolder onCreateViewHolder(ViewGroup parent) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.message_list_card_item, parent, false);
    return new MessageViewHolder(v);
  }

  @Override
  public MessageViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
    return null;
  }

  @Override
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
  }
}