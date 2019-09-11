package com.sticket.app.sticket.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraSurfaceView extends SurfaceView {

    private SurfaceHolder.Callback callback;
    private Context context;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("Surface", "onDraw");
    }

    public CameraSurfaceView(Context context, SurfaceHolder.Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
    }
//
//    public void surfaceCreated(SurfaceHolder holder) {
//        try {
//            // Open the Camera in preview mode
//            this.camera = Camera.open();
//            this.camera.setPreviewDisplay(holder);
//        } catch (IOException ioe) {
//            ioe.printStackTrace(System.out);
//        }
//
//    }
//
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        // Surface will be destroyed when replaced with a new screen
//        // Always make sure to release the Camera instance
//        camera.stopPreview();
//        camera.release();
//        camera = null;
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
    }

    public Bitmap drawBitmap() {
        // https://blog.naver.com/PostView.nhn?blogId=pii90&logNo=90167897138
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        callback.surfaceDestroyed(null); //Thread 잠시 멈춤(pause)
        draw(canvas);
        callback.surfaceCreated(null); //Thread 재개(resume)

        return bitmap;
    }

}