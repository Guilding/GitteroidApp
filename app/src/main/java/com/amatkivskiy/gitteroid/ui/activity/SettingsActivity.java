package com.amatkivskiy.gitteroid.ui.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.presenter.base.BaseActivityPresenter;
import com.amatkivskiy.gitteroid.ui.activity.base.BackActivity;
import com.amatkivskiy.gitteroid.ui.fragment.GitteroidPreferenceFragment;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(SettingsActivity.SettingsActivityPresenter.class)
public class SettingsActivity extends BackActivity<SettingsActivity.SettingsActivityPresenter> {

  public static Intent createIntent(Context context) {
    return new Intent(context, SettingsActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setTitle("Settings");

    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.content, new GitteroidPreferenceFragment());
    ft.commit();
  }

  @Override
  protected int getContentViewResource() {
    return R.layout.settings_activity;
  }

  public static class SettingsActivityPresenter extends BaseActivityPresenter<SettingsActivity> {
  }
}
