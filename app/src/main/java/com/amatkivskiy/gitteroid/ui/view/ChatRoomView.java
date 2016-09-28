package com.amatkivskiy.gitteroid.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.model.MessageModel;
import com.amatkivskiy.gitteroid.model.RoomModel;
import com.amatkivskiy.gitteroid.presenter.ChatRoomViewPresenter;
import com.amatkivskiy.gitteroid.ui.adapter.MessagesAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.layoutmanagers.ScrollSmoothLineaerLayoutManager;
import com.mikepenz.iconics.Iconics;
import com.rey.material.widget.EditText;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(ChatRoomViewPresenter.class)
public class ChatRoomView extends NucleusLinearLayout<ChatRoomViewPresenter> {

  RoomModel room;

  @BindView(R.id.recycler_view)
  UltimateRecyclerView messagesList;

  @BindView(R.id.button_send)
  Button sendButton;

  @BindView(R.id.field_chat_message)
  EditText messageField;

  @BindView(R.id.progress_circular_message)
  ProgressView messageProgressView;

  @BindView(R.id.progress_circular_view)
  ProgressView progressView;

  private ScrollSmoothLineaerLayoutManager layoutManager;

  public ChatRoomView(Context context) {
    super(context);
    init();
  }

  public ChatRoomView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ChatRoomView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  public ChatRoomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    inflate(getContext(), R.layout.fragment_room, this);

    ButterKnife.bind(this);

    new Iconics.IconicsBuilder().ctx(getActivity())
        .style(new ForegroundColorSpan(Color.BLACK))
        .on(sendButton)
        .build();

    sendButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getPresenter().sendMessage(messageField.getText().toString());
      }
    });

    messageField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
          messageField.setError(null);
        }
      }
    });

    messageField.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        messageField.setError(null);
        return false;
      }
    });

    initList();
  }

  private void initList() {
    messagesList.setHasFixedSize(false);

    layoutManager = new ScrollSmoothLineaerLayoutManager(getActivity(),
                                                         LinearLayoutManager.VERTICAL,
                                                         false,
                                                         300);
    layoutManager.setStackFromEnd(true);
    messagesList.setLayoutManager(layoutManager);

    messagesList.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        MessagesAdapter adapter = (MessagesAdapter) messagesList.getAdapter();
        MessageModel first = adapter.getFirstMessage();

        getPresenter().loadMore(first);
      }
    });
  }


  public RoomModel getRoom() {
    return room;
  }

  public void setRoom(RoomModel room) {
    this.room = room;
  }

  public void showMessages(final List<MessageModel> messages) {
    progressView.setVisibility(View.GONE);
    messagesList.setVisibility(View.VISIBLE);

    MessagesAdapter adapter = new MessagesAdapter(getContext(), new ArrayList<>(messages));
    messagesList.setAdapter(adapter);

    messagesList.enableDefaultSwipeRefresh(!messages.isEmpty());
  }

  public void showMoreMessages(List<MessageModel> messages, int firstOfLastItems) {
    MessagesAdapter adapter = (MessagesAdapter) messagesList.getAdapter();

    adapter.updateMessages(messages);
    messagesList.setRefreshing(false);

    layoutManager.scrollToPosition(firstOfLastItems);
  }

  @Override
  public String toString() {
    return "ChatRoomView{" +
           "room=" + room +
           '}';
  }

  public void showMessageFieldError(String errorText) {
    messageField.setError(errorText);
  }

  public void onMessageSend() {
    messageField.setText("");
    showProgress(false);
  }

  public void onMessageSending() {
    showProgress(true);
  }

  public void onMessageSendingFailed() {
    showProgress(false);
  }

  private void showProgress(boolean show) {
    sendButton.setVisibility(show ? View.GONE : View.VISIBLE);
    messageProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
  }
}
