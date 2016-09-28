package com.amatkivskiy.gitteroid.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.di.component.ActivityComponent;
import com.amatkivskiy.gitteroid.di.internals.ForApplication;
import com.amatkivskiy.gitteroid.presenter.base.BasePreferenceFragmentPresenter;
import com.amatkivskiy.gitteroid.ui.activity.base.BaseActivity;
import com.amatkivskiy.gitteroid.ui.fragment.GitteroidPreferenceFragment;

import javax.inject.Inject;

public class PreferenceFragmentPresenter extends BasePreferenceFragmentPresenter<GitteroidPreferenceFragment> {
  @Inject
  @ForApplication
  Context context;

  @Inject
  BaseActivity activity;

  @Override
  public void initComponentAndInjectMembers(ActivityComponent rootComponent) {
    rootComponent.inject(this);
  }

  public boolean onChangelogClick() {
    getView().showChangelogDialog();
    return false;
  }

  public boolean onAboutClicked() {
    getView().showAboutScreen();
    return false;
  }

  public boolean onVisitProjectClicked() {
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setData(Uri.parse(getView().getString(R.string.url_project)));

    getView().startActivity(i);
    return false;
  }
}
