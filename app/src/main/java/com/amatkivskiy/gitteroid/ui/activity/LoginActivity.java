package com.amatkivskiy.gitteroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.presenter.LoginActivityPresenter;
import com.amatkivskiy.gitteroid.ui.activity.base.BaseActivity;
import com.dd.CircularProgressButton;

import butterknife.BindView;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(LoginActivityPresenter.class)
public class LoginActivity extends BaseActivity<LoginActivityPresenter> {

  @BindView(R.id.button_login) CircularProgressButton loginButton;

  public static Intent createIntent(Context context) {
    return new Intent(context, LoginActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getPresenter().login();
      }
    });
  }

  public void stopLoading() {
    this.loginButton.setProgress(100);
  }

  public void showLoading() {
    this.loginButton.setIndeterminateProgressMode(true);
    this.loginButton.setProgress(50);
  }

  public void showError() {
    this.loginButton.setProgress(-1);
  }

  @Override
  protected int getContentViewResource() {
    return R.layout.activity_login;
  }
}
