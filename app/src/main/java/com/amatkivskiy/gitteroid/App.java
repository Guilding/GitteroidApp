package com.amatkivskiy.gitteroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.amatkivskiy.gitteroid.di.component.AndroidApplicationComponent;
import com.amatkivskiy.gitteroid.di.component.DaggerAndroidApplicationComponent;
import com.amatkivskiy.gitteroid.di.module.AndroidModule;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentials;
import com.amatkivskiy.gitter.sdk.credentials.SimpleGitterCredentialsProvider;
import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;

import net.danlew.android.joda.JodaTimeAndroid;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

public class App extends android.app.Application {
  private AndroidApplicationComponent androidComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    androidComponent = DaggerAndroidApplicationComponent.builder()
        .androidModule(new AndroidModule(this)).build();

    initDrawerImageDownloader();

    GitterDeveloperCredentials.init(
        new SimpleGitterCredentialsProvider(
            BuildConfig.OAUTH_KEY,
            BuildConfig.OAUTH_SECRET,
            BuildConfig.REDIRECT_URL));

    JodaTimeAndroid.init(this);

    CustomActivityOnCrash.setShowErrorDetails(true);
    CustomActivityOnCrash.install(this);
  }

  public static AndroidApplicationComponent getAndroidComponent(Context context) {
    return ((App) context.getApplicationContext()).androidComponent;
  }


  private void initDrawerImageDownloader() {
    DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
      @Override
      public void set(ImageView imageView, Uri uri, Drawable placeholder) {
        Glide.with(imageView.getContext())
                .load(uri)
                .placeholder(placeholder)
                .into(imageView);
      }

      @Override
      public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
        set(imageView, uri, placeholder);
      }

      @Override
      public void cancel(ImageView imageView) {
        Glide.clear(imageView);
      }

      @Override
      public Drawable placeholder(Context ctx) {
        return null;
      }

      @Override
      public Drawable placeholder(Context ctx, String tag) {
        return null;
      }
    });
  }
}
