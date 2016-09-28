package com.amatkivskiy.gitteroid.domain.entity;

public class Message {
  public final String id;
  public final String text;
  public final String html;
  public final String sent;
  public final String editedAt;
  public final UserAccount fromUser;
  public final boolean unRead;
  public final int readBy;
  public final int version;

  public Message(String id, String text, String html, String sent, String editedAt,
                 UserAccount fromUser, boolean unRead, int readBy, int version) {
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
    return "Message{" +
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
}
