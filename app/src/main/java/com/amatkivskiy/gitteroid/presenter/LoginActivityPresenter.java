package com.amatkivskiy.gitteroid.presenter;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.di.component.ActivityComponent;
import com.amatkivskiy.gitteroid.di.module.UserAccountManagerModule;
import com.amatkivskiy.gitteroid.presenter.base.BaseActivityPresenter;
import com.amatkivskiy.gitteroid.ui.activity.LoginActivity;
import com.amatkivskiy.gitteroid.ui.activity.MainActivity;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.amatkivskiy.gitteroid.domain.interactor.DefaultSubscriber;
import com.amatkivskiy.gitteroid.domain.interactor.user.GetUserInfoFromInternetUseCase;
import com.amatkivskiy.gitter.sdk.GitterOauthUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LoginActivityPresenter extends BaseActivityPresenter<LoginActivity> {

  @Inject GetUserInfoFromInternetUseCase getUserInfoFromInternetUseCase;
  @Inject @Nullable UserAccount userAccount;

  public void login() {
    openExternalLogin();
  }

  private List<ResolveInfo> getBrowserList() {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sometesturl.com"));
    return getView().getPackageManager().queryIntentActivities(intent, 0);
  }

  @Override
  public void initComponentAndInjectMembers(ActivityComponent rootComponent) {
    rootComponent.plus(new UserAccountManagerModule())
    .inject(this);
  }

  @Override
  protected void onTakeView(LoginActivity view) {
    super.onTakeView(view);

    if (userAccount != null) {
      startActivity(MainActivity.createIntent(view));
      finishActivity();
      return;
    }

    boolean fromLogin = getActivityIntent().getData() != null &&
                        getActivityIntent().getData().getScheme()
                            .equals(getResString(R.string.oauth_scheme));

    if (fromLogin) {
      getView().showLoading();

      Uri uri = getActivityIntent().getData();
      String code = uri.getQueryParameter("code");

      performRequest(code);
    }
  }

  private void performRequest(String code) {
    this.getUserInfoFromInternetUseCase.execute(new DefaultSubscriber<Boolean>() {
      @Override
      public void onCompleted() {
        getView().stopLoading();
        startActivity(MainActivity.createIntent(getView()));
        finishActivity();
      }

      @Override
      public void onError(Throwable e) {
        getView().showError();
      }
    }, code);
  }

  private void openExternalLogin() {
    String url = GitterOauthUtils.buildOauthUrl();

    final List<ResolveInfo> browserList = getBrowserList();
    final List<LabeledIntent> intentList = new ArrayList<>();

    for (final ResolveInfo resolveInfo : browserList) {
      final Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
      newIntent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName,
                                               resolveInfo.activityInfo.name));

      intentList.add(new LabeledIntent(newIntent,
                                       resolveInfo.resolvePackageName,
                                       resolveInfo.labelRes,
                                       resolveInfo.icon));
    }

    final Intent
        chooser =
        Intent.createChooser(intentList.remove(0), "Choose your favorite browser");
    LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);
    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);

    startActivity(chooser);
    finishActivity();
  }

  @Override
  protected void onDropView() {
    super.onDropView();
    this.getUserInfoFromInternetUseCase.unsubscribe();
  }
}
