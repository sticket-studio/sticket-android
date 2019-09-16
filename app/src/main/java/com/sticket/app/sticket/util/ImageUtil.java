package com.sticket.app.sticket.util;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.sticket.app.sticket.util.camera_setting.CameraOption;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ImageUtil {
    public static final String IMG_FORMAT = ".jpg";

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

    public static void store(Context context, Bitmap bitmap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS", Locale.KOREA);
        String albumPath = FileUtil.ALBUM_DIRECTORY_PATH;
        String imgName = albumPath + "/" + sdf.format(new Date()) + IMG_FORMAT;

        int quality = CameraOption.getInstance().ishD() ? 95 : 60;

        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, new FileOutputStream(imgName));
        } catch (FileNotFoundException e) {
            Log.e("CAPTURE", e.getMessage());
            e.printStackTrace();
        }

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA,
                imgName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        context.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();

        matrix.postRotate(degree);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
    }
}