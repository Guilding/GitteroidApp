package com.amatkivskiy.gitteroid.ui.fragment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amatkivskiy.gitteroid.presenter.base.BaseFragmentPresenter;
import com.hannesdorfmann.fragmentargs.FragmentArgs;

import butterknife.ButterKnife;
import nucleus.view.NucleusSupportFragment;

public abstract class BaseFragment<PresenterType extends BaseFragmentPresenter>
    extends NucleusSupportFragment<PresenterType> {

  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    FragmentArgs.inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(getFragmentLayout(), container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    injectViews(view);
  }

  protected abstract int getFragmentLayout();

  private void injectViews(View view) {
    ButterKnife.bind(this, view);
  }
}