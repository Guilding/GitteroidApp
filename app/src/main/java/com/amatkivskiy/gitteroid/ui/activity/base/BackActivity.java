package com.amatkivskiy.gitteroid.ui.activity.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.presenter.base.BaseActivityPresenter;

public abstract class BackActivity<PresenterType extends BaseActivityPresenter> extends BaseActivity<PresenterType> {

  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (isToolbarEnabled()) {
      toolbar = (Toolbar) findViewById(getToolbarId());

      if (toolbar != null) {
        toolbar.setTitle(R.string.app_name);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            finish();
          }
        });
      }
    }
  }

  public boolean isToolbarEnabled() {
    return true;
  }

  public Toolbar getToolbar() {
    return toolbar;
  }

  public int getToolbarId() {
    return R.id.toolbar;
  }

  @Override
  public void setTitle(CharSequence title) {
    if (toolbar != null) {
      toolbar.setTitle(title);
    } else {
      super.setTitle(title);
    }
  }

  @Override
  public void setTitle(int titleId) {
    if (toolbar != null) {
      toolbar.setTitle(titleId);
    }
    super.setTitle(titleId);
  }
}
