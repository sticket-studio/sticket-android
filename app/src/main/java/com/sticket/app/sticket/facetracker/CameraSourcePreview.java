/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sticket.app.sticket.facetracker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.common.images.Size;
import com.google.android.gms.vision.CameraSource;
import com.sticket.app.sticket.util.CameraSurfaceView;
import com.sticket.app.sticket.util.CameraUtil;
import com.sticket.app.sticket.util.ImageUtil;
import com.sticket.app.sticket.util.PxDpUtil;
import com.sticket.app.sticket.util.camera_setting.CameraOption;
import com.sticket.app.sticket.util.camera_setting.Ratio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CameraSourcePreview extends ViewGroup {
    private static final String TAG = "CameraSourcePreview";

    private Context mContext;
    private CameraSurfaceView mSurfaceView;
    private boolean mStartRequested;
    private boolean mSurfaceAvailable;
    private CameraSource mCameraSource;

    private GraphicOverlay mOverlay;

    public CameraSourcePreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mStartRequested = false;
        mSurfaceAvailable = false;

        SurfaceCallback callback = new SurfaceCallback();
        mSurfaceView = new CameraSurfaceView(context, callback);
        mSurfaceView.getHolder().addCallback(callback);
        addView(mSurfaceView);
    }

    public void start(CameraSource cameraSource) throws IOException {
        if (cameraSource == null) {
            stop();
        }

        mCameraSource = cameraSource;

        if (mCameraSource != null) {
            mStartRequested = true;
            startIfReady();
        }
    }

    public void start(CameraSource cameraSource, GraphicOverlay overlay) throws IOException {
        mOverlay = overlay;
        start(cameraSource);
    }

    public void stop() {
        if (mCameraSource != null) {
            mCameraSource.stop();
        }
    }

    public void release() {
        if (mCameraSource != null) {
            mCameraSource.release();
            mCameraSource = null;
        }
    }

    private void startIfReady() throws IOException {
        if (mStartRequested && mSurfaceAvailable) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mCameraSource.start(mSurfaceView.getHolder());
            if (mOverlay != null) {
                Size size = mCameraSource.getPreviewSize();
                int min = Math.min(size.getWidth(), size.getHeight());
                int max = Math.max(size.getWidth(), size.getHeight());
                if (isPortraitMode()) {
                    // Swap width and height sizes when in portrait, since it will be rotated by
                    // 90 degrees
                    mOverlay.setCameraInfo(min, max, mCameraSource.getCameraFacing());
                } else {
                    mOverlay.setCameraInfo(max, min, mCameraSource.getCameraFacing());
                }
                mOverlay.clear();
            }
            mStartRequested = false;
        }
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder surface) {
            mSurfaceAvailable = true;
            try {
                startIfReady();
            } catch (IOException e) {
                Log.e(TAG, "Could not start camera source.", e);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surface) {
            mSurfaceAvailable = false;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera mCamera = CameraUtil.getCamera(mCameraSource);
//            try {
//                mCamera.setPreviewDisplay(holder);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mCamera.setPreviewCallback((data, camera) -> {
                // 현재 SurfaceView를 JPEG Format으로 변경
//                Camera.Parameters parameters = camera.getParameters();
//                int w = parameters.getPreviewSize().width;
//                int h = parameters.getPreviewSize().height;
//                int format1 = parameters.getPreviewFormat();
//                YuvImage image = new YuvImage(data, format1, w, h, null);
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                Rect area = new Rect(0, 0, w, h);
//                image.compressToJpeg(area, 100, out);
//                Bitmap bm = BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.size());
//                byte[] currentData = out.toByteArray();
//            });
        }
    }

    private int cnt = 0;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        setRatio();
    }

    public CameraSurfaceView getmSurfaceView() {
        return mSurfaceView;
    }

    public void setRatio() {
        Ratio ratio = CameraOption.getInstance().getRatio();
        int ratioWidth = ratio.getWidth();
        int ratioHeight = ratio.getHeight();

        int marginTop = 0;
        if (ratio != Ratio.RATIO_9_16) {
            marginTop = 90;
        }
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) getLayoutParams();
        layoutParams.setMargins(0, (int) PxDpUtil.convertDpToPixel(marginTop, getContext()), 0, 0);
        setLayoutParams(layoutParams);

        if (mCameraSource != null) {
            Log.e("PREVIEW", "cameraSource != null : ");
            Size size = mCameraSource.getPreviewSize();
            if (size != null) {
                Log.e("PREVIEW", "size != null : ");
                ratioWidth = size.getWidth();
                ratioHeight = size.getHeight();
            }
        }

        // Swap width and height sizes when in portrait, since it will be rotated 90 degrees
        if (!isPortraitMode()) {
            int tmp = ratioWidth;
            ratioWidth = ratioHeight;
            ratioHeight = tmp;
        }

        float ratioVal = (float) ratioWidth / (float) ratioHeight;

        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();

        int layoutWidth = dm.widthPixels;
        int layoutHeight = dm.heightPixels;

        // Computes height and width for potentially doing fit width.
        int childWidth = layoutWidth;
        int childHeight = (int) ((float) layoutWidth * ratioVal);

        // If height is too tall using fit width, does fit height instead.
        if (ratio == Ratio.RATIO_9_16 || childHeight > layoutHeight) {
            childHeight = layoutHeight;
            childWidth = (int) ((float) layoutHeight / ratioVal);
        }

        for (int i = 0; i < getChildCount(); ++i) {
            getChildAt(i).layout(0, 0, childWidth, childHeight);
            Log.d(TAG, "Assigned view: " + i);
        }

        Log.e(TAG, "layoutWidth :  " + layoutWidth);
        Log.e(TAG, "layoutHeight :  " + layoutHeight);
        Log.e(TAG, "childWidth :  " + childWidth);
        Log.e(TAG, "childHeight :  " + childHeight);

        try {
            startIfReady();
        } catch (IOException e) {
            Log.e(TAG, "Could not start camera source.", e);
        }
    }

    private boolean isPortraitMode() {
        int orientation = mContext.getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
