package com.sticket.app.sticket.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
    public static Bitmap getBitmapFromView(Activity activity, View view, int width, int height) {
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

        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true).copy(Bitmap.Config.ARGB_8888, false);
        view.destroyDrawingCache();

        return flipBitmap(bitmap);
    }

    static public Bitmap resizeBitmap(Bitmap original, int width, int height) {
        return Bitmap.createScaledBitmap(original, width, height, false);
    }

    static public Bitmap flipBitmap(Bitmap original) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(original, 0,0,original.getWidth(), original.getHeight(), matrix, false);
    }

    public static Bitmap combineImages(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        // 왠지 모르겠는데 뒤집혀서 나옴;
        Bitmap flipedBitmap = resizeBitmap(bmp2, bmp1.getWidth(), bmp1.getHeight());
        canvas.drawBitmap(flipedBitmap, 0, 0, null);
        return bmOverlay;
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

    public static Bitmap rotateBitmap(Bitmap bitmap, int degree, boolean isFlipped) {
        Matrix matrix = new Matrix();

        if(isFlipped){
            matrix.setScale(-1,1);
            matrix.postTranslate(bitmap.getWidth(),0);
        }
        matrix.postRotate(degree);

        Bitmap rotated = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        return Bitmap.createBitmap(rotated, 0, 0, rotated.getWidth(), rotated.getHeight(), matrix, true);
    }
}