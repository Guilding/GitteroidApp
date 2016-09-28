package com.amatkivskiy.gitteroid.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.amatkivskiy.gitteroid.data.R;
import com.amatkivskiy.gitteroid.domain.entity.UserAccount;
import com.google.gson.Gson;

public class ComplexPreferences {

  private static ComplexPreferences complexPreferences;

  private SharedPreferences preferences;
  private SharedPreferences.Editor editor;
  private Gson gson;

  private Context context;

  private ComplexPreferences(Context context, String namePreferences, int mode) {
    preferences = context.getSharedPreferences(namePreferences, mode);
    editor = preferences.edit();
    gson = new Gson();
    this.context = context;
  }

  private ComplexPreferences(Context context) {
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
    editor = preferences.edit();
    gson = new Gson();
    this.context = context;
  }

  public static ComplexPreferences getComplexPreferences(Context context,
                                                         String namePreferences, int mode) {
    if (complexPreferences == null) {
      complexPreferences = new ComplexPreferences(context,
          namePreferences, mode);
    }

    return complexPreferences;
  }

  public static ComplexPreferences getComplexPreferences(Context context) {
    if (complexPreferences == null) {
      complexPreferences = new ComplexPreferences(context);
    }

    return complexPreferences;
  }


  public UserAccount getAccount() {
    return this.getObject(getKeyFromResources(R.string.key_user_account), UserAccount.class);
  }

  public void saveAccount(UserAccount account) {
    if (account != null) {
      this.putObject(getKeyFromResources(R.string.key_user_account), account);
    } else {
      this.remove(getKeyFromResources(R.string.key_user_account));
    }
  }

  public int getNumberOfRoomsInDrawer() {
    String defaultValue = String.valueOf(context.getResources().getInteger(R.integer.default_drawer_rooms_number));
    String value = this.preferences.getString(getKeyFromResources(R.string.key_drawer_rooms_number), defaultValue);

    return Integer.parseInt(value);
  }

  private String getKeyFromResources(@StringRes int resourceId) {
    return context.getString(resourceId);
  }

  //Internals
  private void putObject(String key, Object object) {
    if (object == null) {
      throw new IllegalArgumentException("object is null");
    }

    if (TextUtils.isEmpty(key)) {
      throw new IllegalArgumentException("key is empty or null");
    }

    editor.putString(key, gson.toJson(object));
    editor.commit();
  }

  public void commit() {
    editor.commit();
  }

  private <T> T getObject(String key, Class<T> a) {

    String gson = preferences.getString(key, null);
    if (gson == null) {
      return null;
    } else {
      try {
        return this.gson.fromJson(gson, a);
      } catch (Exception e) {
        throw new IllegalArgumentException(
            "Object storaged with key " + key + " is instanceof other class");
      }
    }
  }

  public void remove(String key) {
    editor.remove(key).commit();
  }
}