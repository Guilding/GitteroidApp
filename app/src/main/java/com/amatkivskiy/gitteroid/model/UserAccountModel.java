package com.amatkivskiy.gitteroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserAccountModel implements Parcelable {
  public final String token;
  public final String username;
  public final String id;
  public final String displayName;
  public final String avatarUrlMedium;

  public UserAccountModel(String token, String username, String id, String displayName,
                     String avatarUrlMedium) {
    this.token = token;
    this.username = username;
    this.id = id;
    this.displayName = displayName;
    this.avatarUrlMedium = avatarUrlMedium;
  }

  @Override
  public String toString() {
    return "UserAccountModel{" +
           "token='" + token + '\'' +
           ", username='" + username + '\'' +
           ", id='" + id + '\'' +
           ", displayName='" + displayName + '\'' +
           ", avatarUrlMedium='" + avatarUrlMedium + '\'' +
           '}';
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.token);
    dest.writeString(this.username);
    dest.writeString(this.id);
    dest.writeString(this.displayName);
    dest.writeString(this.avatarUrlMedium);
  }

  protected UserAccountModel(Parcel in) {
    this.token = in.readString();
    this.username = in.readString();
    this.id = in.readString();
    this.displayName = in.readString();
    this.avatarUrlMedium = in.readString();
  }

  public static final Parcelable.Creator<UserAccountModel>
      CREATOR =
      new Parcelable.Creator<UserAccountModel>() {
        public UserAccountModel createFromParcel(Parcel source) {
          return new UserAccountModel(source);
        }

        public UserAccountModel[] newArray(int size) {
          return new UserAccountModel[size];
        }
      };
}
