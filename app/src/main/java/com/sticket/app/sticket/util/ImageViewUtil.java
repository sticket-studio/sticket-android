package com.sticket.app.sticket.util;

import android.graphics.Matrix;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageViewUtil {
    public static int[] getBitmapOffset(ImageView img, Boolean includeLayout) {
        int[] offset = new int[2];
        float[] values = new float[9];

        Matrix m = img.getImageMatrix();
        m.getValues(values);

        offset[0] = (int) values[5];
        offset[1] = (int) values[2];

        if (includeLayout) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) img.getLayoutParams();
            int paddingTop = img.getPaddingTop();
            int paddingLeft = img.getPaddingLeft();

            offset[0] += paddingTop + lp.topMargin;
            offset[1] += paddingLeft + lp.leftMargin;
        }
        return offset;
    }
}
