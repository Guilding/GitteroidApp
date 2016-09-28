package com.amatkivskiy.gitteroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomModel implements Parcelable {
  public final String id;
  public final String name;
  public final int unreadItems;
  public final int mentions;
  public final String lastAccessTime;
  public final String roomIconUrl;
  public final String groupName;
  public final String generalName;

  public RoomModel(String id, String name, int unreadItems, int mentions, String lastAccessTime,
                   String roomIconUrl, String groupName, String generalName) {
    this.id = id;
    this.name = name;
    this.unreadItems = unreadItems;
    this.mentions = mentions;
    this.lastAccessTime = lastAccessTime;
    this.roomIconUrl = roomIconUrl;
    this.groupName = groupName;
    this.generalName = generalName;
  }

  @Override
  public String toString() {
    return "RoomModel{" +
        "generalName='" + generalName + '\'' +
        '}';
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.name);
    dest.writeInt(this.unreadItems);
    dest.writeInt(this.mentions);
    dest.writeString(this.lastAccessTime);
    dest.writeString(this.roomIconUrl);
    dest.writeString(this.groupName);
    dest.writeString(this.generalName);
  }

  protected RoomModel(Parcel in) {
    this.id = in.readString();
    this.name = in.readString();
    this.unreadItems = in.readInt();
    this.mentions = in.readInt();
    this.lastAccessTime = in.readString();
    this.roomIconUrl = in.readString();
    this.groupName = in.readString();
    this.generalName = in.readString();
  }

  public static final Creator<RoomModel> CREATOR = new Creator<RoomModel>() {
    public RoomModel createFromParcel(Parcel source) {
      return new RoomModel(source);
    }

    public RoomModel[] newArray(int size) {
      return new RoomModel[size];
    }
  };
}
