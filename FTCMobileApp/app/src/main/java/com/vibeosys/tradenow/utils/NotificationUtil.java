package com.vibeosys.tradenow.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.views.BadgeDrawable;

/**
 * Created by akshay on 18-06-2016.
 */
public class NotificationUtil {

    static BadgeDrawable badge;

    public NotificationUtil(Context context, LayerDrawable icon) {
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public static void setBadgeCount(int count) {
        // Reuse drawable if possible
        badge.setCount(count);
    }
}
