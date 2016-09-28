package com.amatkivskiy.gitteroid.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;

import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.presenter.PreferenceFragmentPresenter;
import com.amatkivskiy.gitteroid.ui.activity.base.NucleusPreferenceFragment;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(PreferenceFragmentPresenter.class)
public class GitteroidPreferenceFragment extends NucleusPreferenceFragment<PreferenceFragmentPresenter>
    implements Preference.OnPreferenceClickListener {

  private ChangelogDialog dialog;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    addPreferencesFromResource(R.xml.main_prefs);

    findPreference(getString(R.string.key_changelog)).setOnPreferenceClickListener(this);
    findPreference(getString(R.string.key_about)).setOnPreferenceClickListener(this);
    findPreference(getString(R.string.key_visit_project)).setOnPreferenceClickListener(this);
  }

  public boolean onPreferenceClick(Preference preference) {
    if (preference.getKey().equals(getString(R.string.key_changelog))) {
      return getPresenter().onChangelogClick();
    } else if (preference.getKey().equals(getString(R.string.key_about))) {
      return getPresenter().onAboutClicked();
    } else if (preference.getKey().equals(getString(R.string.key_visit_project))) {
      return getPresenter().onVisitProjectClicked();
    }

    return false;
  }

  @Override
  public void onStop() {
    try {
      if (dialog != null) {
        dialog.dismiss();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    super.onStop();
  }

  public void showChangelogDialog() {
    dialog = ChangelogDialog.create();
    dialog.show(getFragmentManager(), "changelog");
  }

  public void showAboutScreen() {
    new LibsBuilder()
        //Pass the fields of your application to the lib so it can find all external lib information
        .withFields(R.string.class.getFields())
        .withActivityTitle(getString(R.string.app_name))
        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
        .withActivityTheme(R.style.MaterialTheme_Light_DarkToolbar)
        .start(getActivity());
  }
}

