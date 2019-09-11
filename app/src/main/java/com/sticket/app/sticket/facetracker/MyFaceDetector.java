package com.sticket.app.sticket.facetracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;

import java.io.ByteArrayOutputStream;

public class MyFaceDetector extends Detector<Face> {
    private Detector<Face> mDelegate;
    private Bitmap currentBitmap;

    public MyFaceDetector(Detector<Face> delegate) {
        mDelegate = delegate;
    }

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    @Override
    public SparseArray<Face> detect(Frame frame) {
        Log.e("MyFaceDetector","detect");
        int width = frame.getMetadata().getWidth();
        int height = frame.getMetadata().getHeight();

        YuvImage yuvImage = new YuvImage(frame.getGrayscaleImageData().array(), ImageFormat.NV21, width, height, null);
        Log.e("MyFaceDetector","frame.getMetadata().getWidth() : " + frame.getMetadata().getWidth());
        Log.e("MyFaceDetector","frame.getMetadata().getHeight() : " + frame.getMetadata().getHeight());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 100, byteArrayOutputStream);
        byte[] jpegArray = byteArrayOutputStream.toByteArray();
        this.currentBitmap = BitmapFactory.decodeByteArray(jpegArray, 0, jpegArray.length);

        return mDelegate.detect(frame);
    }

    @Override
    public boolean isOperational() {
        return mDelegate.isOperational();
    }

    @Override
    public boolean setFocus(int id) {
        return mDelegate.setFocus(id);
    }
}