package com.amatkivskiy.gitteroid.presenter.base;

import android.view.View;

import com.amatkivskiy.gitteroid.di.NeedComponentInitialization;
import com.amatkivskiy.gitteroid.di.component.ActivityComponent;
import com.amatkivskiy.gitteroid.ui.activity.base.BaseActivity;

import nucleus.presenter.Presenter;

public abstract class BaseViewPresenter<ViewType extends View> extends Presenter<ViewType> implements NeedComponentInitialization<ActivityComponent> {

  @Override
  protected void onTakeView(ViewType view) {
    if (view.getContext() instanceof BaseActivity) {
      BaseActivity activity = (BaseActivity)view.getContext();
      initComponentAndInjectMembers(activity.getComponent());
    } else {
      throw new IllegalStateException("View context should be subtype of BaseActivity");
    }
  }
}
