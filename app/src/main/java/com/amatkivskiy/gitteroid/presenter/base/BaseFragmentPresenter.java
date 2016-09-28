package com.amatkivskiy.gitteroid.presenter.base;

import com.amatkivskiy.gitteroid.di.NeedComponentInitialization;
import com.amatkivskiy.gitteroid.di.component.ActivityComponent;
import com.amatkivskiy.gitteroid.ui.activity.base.BaseActivity;
import com.amatkivskiy.gitteroid.ui.fragment.base.BaseFragment;

import nucleus.presenter.Presenter;

public abstract class BaseFragmentPresenter<ViewType extends BaseFragment>
    extends Presenter<ViewType> implements NeedComponentInitialization<ActivityComponent> {

  @Override
  public abstract void initComponentAndInjectMembers(ActivityComponent activityComponent);

  @Override
  protected void onTakeView(ViewType view) {
    BaseActivity activity = (BaseActivity) getView().getActivity();
    initComponentAndInjectMembers(activity.getComponent());
  }
}
