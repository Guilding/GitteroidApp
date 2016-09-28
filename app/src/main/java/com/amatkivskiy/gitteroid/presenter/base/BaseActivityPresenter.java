package com.amatkivskiy.gitteroid.presenter.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;

import com.amatkivskiy.gitteroid.di.NeedComponentInitialization;
import com.amatkivskiy.gitteroid.di.component.ActivityComponent;
import com.amatkivskiy.gitteroid.ui.activity.base.BaseActivity;

import icepick.Icepick;
import nucleus.presenter.Presenter;

public abstract class BaseActivityPresenter<ViewType extends BaseActivity> extends Presenter<ViewType>
    implements NeedComponentInitialization<ActivityComponent>{

  @Override
  protected void onCreate(Bundle savedState) {
    super.onCreate(savedState);
    Icepick.restoreInstanceState(this, savedState);
  }

  @Override
  public void initComponentAndInjectMembers(ActivityComponent rootComponent) {

  }

  @Override
  protected void onTakeView(ViewType view) {
    initComponentAndInjectMembers(view.getComponent());
  }

  protected void startActivity(Intent intent) {
    getView().startActivity(intent);
  }

  @Override
  public void save(Bundle state) {
    super.save(state);
    Icepick.saveInstanceState(this, state);
  }

  protected void finishActivity() {
    getView().finish();
  }

  protected Intent getActivityIntent() {
    return getView().getIntent();
  }

  protected String getResString(@StringRes int stringId) {
    return getView().getString(stringId);
  }
}
