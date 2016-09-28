package com.amatkivskiy.gitteroid.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.amatkivskiy.gitteroid.App;
import com.amatkivskiy.gitteroid.di.NeedComponentInitialization;
import com.amatkivskiy.gitteroid.di.component.ActivityComponent;
import com.amatkivskiy.gitteroid.di.component.AndroidApplicationComponent;
import com.amatkivskiy.gitteroid.di.module.ActivityModule;
import com.amatkivskiy.gitteroid.presenter.base.BaseActivityPresenter;

import butterknife.ButterKnife;
import nucleus.view.NucleusAppCompatActivity;

public abstract class BaseActivity<PresenterType extends BaseActivityPresenter>
    extends NucleusAppCompatActivity<PresenterType>
    implements NeedComponentInitialization<ActivityComponent> {

  private ActivityComponent component;

  @Override
  public void initComponentAndInjectMembers(ActivityComponent rootComponent) {

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(getContentViewResource());
    ButterKnife.bind(this);

    AndroidApplicationComponent androidComponent = App.getAndroidComponent(this);

    component = androidComponent.plus(new ActivityModule(this));
    initComponentAndInjectMembers(component);
  }

  public ActivityComponent getComponent() {
    return component;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    component = null;
  }

  @LayoutRes
  protected abstract int getContentViewResource();
}