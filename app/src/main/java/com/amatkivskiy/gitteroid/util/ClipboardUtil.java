package com.amatkivskiy.gitteroid.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import static android.content.Context.CLIPBOARD_SERVICE;

public class ClipboardUtil {

  public static void copyErrorToClipboard(Context context, String text) {
    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
    ClipData clip = ClipData.newPlainText("Text", text);
    clipboard.setPrimaryClip(clip);
  }
}
