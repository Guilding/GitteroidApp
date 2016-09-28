package com.amatkivskiy.gitteroid.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amatkivskiy.gitteroid.R;
import com.mikepenz.iconics.Iconics;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabWithCloseButtonView extends RelativeLayout {
  @BindView(R.id.text_view_tab_title)
  TextView tabTitle;

  @BindView(R.id.button_close_tab)
  Button closeButton;

  public TabWithCloseButtonView(Context context) {
    super(context);
    init();
  }

  public TabWithCloseButtonView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public TabWithCloseButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public TabWithCloseButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    inflate(getContext(), R.layout.view_custom_tab, this);
    ButterKnife.bind(this);

    new Iconics.IconicsBuilder().ctx(getContext())
        .style(new ForegroundColorSpan(Color.WHITE))
        .on(closeButton)
        .build();
  }

  public void setTitle(String title) {
    tabTitle.setText(title);
  }

  /*
  Makes close button visible.
   */
  public void switchCloseState(boolean closeEnabled) {
    closeButton.setVisibility(closeEnabled ? VISIBLE : GONE);
  }

  public void setCloseButtonOnClickListener(OnClickListener listener) {
    closeButton.setOnClickListener(listener);
  }

  public boolean isCloseStateEnabled() {
    return closeButton.getVisibility() == VISIBLE;
  }
}
