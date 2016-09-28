package com.amatkivskiy.gitteroid.util;

import android.content.Context;
import android.graphics.Color;

import com.mikepenz.materialdrawer.holder.BadgeStyle;

public class BadgeUtils {

  public static String getUnreadItemsText(int unReadItemsCount) {
    return unReadItemsCount >= 100 ? "99+" : String.valueOf(unReadItemsCount);
  }

  public static String getMentionedItemsText(int mentionedItemsCount) {
    return mentionedItemsCount >= 100 ? "99+" : String.valueOf(mentionedItemsCount);
  }

  public static BadgeStyle getUnReadBadgeStyle(Context context) {
    return new BadgeStyle()
        .withTextColor(Color.WHITE)
        .withColor(Color.parseColor("#45CBA1"))
        .withCorners(20);
  }

  public static BadgeStyle getMentionedBadgeStyle(Context context) {
    return new BadgeStyle()
        .withTextColor(Color.WHITE)
        .withColor(Color.parseColor("#e67e22"))
        .withCorners(20);
  }
}
