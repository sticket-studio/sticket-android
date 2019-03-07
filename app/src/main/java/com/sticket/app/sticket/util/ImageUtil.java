package com.sticket.app.sticket.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;

public class ImageUtil {

    /**
     * Get bitmap of a view
     *
     * @param view source view
     * @return generated bitmap object
     */
    public static Bitmap getBitmapFromView(View view) {
        // TODO: getWidth() to getMeasuredWidth()
        // 여기서 원래 measure를 해줌
        // 근데 문제는 measure를 해주면 값이 0이 되어 에러가 남.
        // 그래서 걍 getWidth(), getHeight()값 바로 집어넣어 버림.
        // 문제생기면 나중에 고치자!
//        Log.e("CAPTURE", "width: " + view.getWidth());
//        Log.e("CAPTURE", "height: " + view.getHeight());
//        view.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);
//        Log.e("CAPTURE", "width: " + view.getMeasuredWidth());
//        Log.e("CAPTURE", "height: " + view.getMeasuredHeight());
//        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getWidth(), view.getHeight());
        view.draw(canvas);
        return bitmap;
    }

    /**
     * Stitch two images one below another
     *
     * @param listOfBitmapsToStitch List of bitmaps to stitch
     * @return resulting stitched bitmap
     */
    public static Bitmap combineImages(ArrayList<Bitmap> listOfBitmapsToStitch) {
        Bitmap bitmapResult = null;

        int width = 0, height = 0;

        for (Bitmap bitmap : listOfBitmapsToStitch) {
            width = Math.max(width, bitmap.getWidth());
            height = height + bitmap.getHeight();
        }

        bitmapResult = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImageCanvas = new Canvas(bitmapResult);

        int currentHeight = 0;
        for (Bitmap bitmap : listOfBitmapsToStitch) {
            comboImageCanvas.drawBitmap(bitmap, 0f, currentHeight, null);
            currentHeight = currentHeight + bitmap.getHeight();
        }

        return bitmapResult;
    }
}