package com.amatkivskiy.gitteroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageModel implements Parcelable {
  public final String id;
  public final String text;
  public final String html;
  public final String sent;
  public final String editedAt;
  public final UserAccountModel fromUser;
  public final boolean unRead;
  public final int readBy;
  public final int version;

  public MessageModel(String id, String text, String html, String sent, String editedAt,
                      UserAccountModel fromUser, boolean unRead, int readBy, int version) {
    this.id = id;
    this.text = text;
    this.html = html;
    this.sent = sent;
    this.editedAt = editedAt;
    this.fromUser = fromUser;
    this.unRead = unRead;
    this.readBy = readBy;
    this.version = version;
  }

  @Override
  public String toString() {
    return "MessageModel{" +
           "id='" + id + '\'' +
           ", text='" + text + '\'' +
           ", html='" + html + '\'' +
           ", sent='" + sent + '\'' +
           ", editedAt='" + editedAt + '\'' +
           ", fromUser=" + fromUser +
           ", unRead=" + unRead +
           ", readBy=" + readBy +
           ", version=" + version +
           '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    MessageModel that = (MessageModel) o;

    return id.equals(that.id);

  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.text);
    dest.writeString(this.html);
    dest.writeString(this.sent);
    dest.writeString(this.editedAt);
    dest.writeParcelable(this.fromUser, flags);
    dest.writeByte(unRead ? (byte) 1 : (byte) 0);
    dest.writeInt(this.readBy);
    dest.writeInt(this.version);
  }

  protected MessageModel(Parcel in) {
    this.id = in.readString();
    this.text = in.readString();
    this.html = in.readString();
    this.sent = in.readString();
    this.editedAt = in.readString();
    this.fromUser = in.readParcelable(UserAccountModel.class.getClassLoader());
    this.unRead = in.readByte() != 0;
    this.readBy = in.readInt();
    this.version = in.readInt();
  }

  public static final Parcelable.Creator<MessageModel>
      CREATOR =
      new Parcelable.Creator<MessageModel>() {
        public MessageModel createFromParcel(Parcel source) {
          return new MessageModel(source);
        }

        public MessageModel[] newArray(int size) {
          return new MessageModel[size];
        }
      };
}
