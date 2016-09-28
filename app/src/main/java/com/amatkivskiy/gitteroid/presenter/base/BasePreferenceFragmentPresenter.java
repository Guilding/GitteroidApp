package com.amatkivskiy.gitteroid.presenter.base;

import android.preference.PreferenceFragment;

import com.amatkivskiy.gitteroid.di.NeedComponentInitialization;
import com.amatkivskiy.gitteroid.di.component.ActivityComponent;
import com.amatkivskiy.gitteroid.ui.activity.base.BaseActivity;

import nucleus.presenter.Presenter;

public abstract class BasePreferenceFragmentPresenter<ViewType extends PreferenceFragment>
    extends Presenter<ViewType>
    implements NeedComponentInitialization<ActivityComponent> {

  @Override
  protected void onTakeView(ViewType view) {
    BaseActivity activity = (BaseActivity)view.getActivity();
    initComponentAndInjectMembers(activity.getComponent());
  }
}
