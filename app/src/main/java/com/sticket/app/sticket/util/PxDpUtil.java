package com.sticket.app.sticket.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class PxDpUtil {
    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */

    public static float convertDpToPixel(float dp, Context context) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);

    }


    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */

    public static float convertPixelsToDp(float px, Context context) {

        float density = context.getResources().getDisplayMetrics().density;

        if (density == 1.0) {
            density *= 4.0;
        } else if (density == 1.5) {
            density *= (8.0 / 3.0);
        } else if (density == 2.0) {
            density *= 2.0;
        }
        return px / density;
    }
}
