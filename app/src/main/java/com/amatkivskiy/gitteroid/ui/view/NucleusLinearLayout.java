package com.amatkivskiy.gitteroid.ui.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import nucleus.factory.PresenterFactory;
import nucleus.factory.ReflectionPresenterFactory;
import nucleus.presenter.Presenter;
import nucleus.view.PresenterLifecycleDelegate;
import nucleus.view.ViewWithPresenter;

public class NucleusLinearLayout<P extends Presenter> extends LinearLayout implements ViewWithPresenter<P> {

  private static final String PARENT_STATE_KEY = "parent_state";
  private static final String PRESENTER_STATE_KEY = "presenter_state";

  private PresenterLifecycleDelegate<P> presenterDelegate =
          new PresenterLifecycleDelegate<>(ReflectionPresenterFactory.<P>fromViewClass(getClass()));

  public NucleusLinearLayout(Context context) {
    super(context);
  }

  public NucleusLinearLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public NucleusLinearLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public NucleusLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  /**
   * Returns a current presenter factory.
   */
  public PresenterFactory<P> getPresenterFactory() {
    return presenterDelegate.getPresenterFactory();
  }

  /**
   * Sets a presenter factory.
   * Call this method before onCreate/onFinishInflate to override default {@link ReflectionPresenterFactory} presenter factory.
   * Use this method for presenter dependency injection.
   */
  @Override
  public void setPresenterFactory(PresenterFactory<P> presenterFactory) {
    presenterDelegate.setPresenterFactory(presenterFactory);
  }

  /**
   * Returns a current attached presenter.
   * This method is guaranteed to return a non-null value between
   * onResume/onPause and onAttachedToWindow/onDetachedFromWindow calls
   * if the presenter factory returns a non-null value.
   *
   * @return a currently attached presenter or null.
   */
  public P getPresenter() {
    return presenterDelegate.getPresenter();
  }

  /**
   * Returns the unwrapped activity of the view or throws an exception.
   *
   * @return an unwrapped activity
   */
  public Activity getActivity() {
    Context context = getContext();
    while (!(context instanceof Activity) && context instanceof ContextWrapper)
      context = ((ContextWrapper) context).getBaseContext();
    if (!(context instanceof Activity))
      throw new IllegalStateException("Expected an activity context, got " + context.getClass().getSimpleName());
    return (Activity) context;
  }

  @Override
  protected Parcelable onSaveInstanceState() {
    Bundle bundle = new Bundle();
    bundle.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState());
    bundle.putParcelable(PARENT_STATE_KEY, super.onSaveInstanceState());
    return bundle;
  }

  @Override
  protected void onRestoreInstanceState(Parcelable state) {
    Bundle bundle = (Bundle) state;
    super.onRestoreInstanceState(bundle.getParcelable(PARENT_STATE_KEY));
    presenterDelegate.onRestoreInstanceState(bundle.getBundle(PRESENTER_STATE_KEY));
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (!isInEditMode())
      presenterDelegate.onResume(this);
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    presenterDelegate.onDropView();
    presenterDelegate.onDestroy(!getActivity().isChangingConfigurations());
  }
}