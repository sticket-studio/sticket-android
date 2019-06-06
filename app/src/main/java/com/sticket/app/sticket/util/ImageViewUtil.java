package com.sticket.app.sticket.util;

import android.widget.ImageView;

public class ImageViewUtil {
    public static float getAbstractXByPercent(ImageView imageView, float x) {
        int width = imageView.getDrawable().getIntrinsicWidth();

        return width * x / 100f;
    }

    public static float getAbstractYByPercent(ImageView imageView, float y) {
        int height = imageView.getDrawable().getIntrinsicHeight();

        return height * y / 100f;
    }
}
