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
import com.sticket.app.sticket.util.PxDpUtil;
import com.sticket.app.sticket.util.camera_setting.CameraOption;
import com.sticket.app.sticket.util.camera_setting.Ratio;

import java.io.IOException;

public class CameraSourcePreview extends ViewGroup {
    private static final String TAG = "CameraSourcePreview";

    private Context context;
    private CameraSurfaceView surfaceView;
    private boolean startRequested;
    private boolean surfaceAvailable;
    private CameraSource cameraSource;

    private GraphicOverlay overlay;

    public CameraSourcePreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        startRequested = false;
        surfaceAvailable = false;

        SurfaceCallback callback = new SurfaceCallback();
        surfaceView = new CameraSurfaceView(context, callback);
        surfaceView.getHolder().addCallback(callback);
        addView(surfaceView);
    }

    public void start(CameraSource cameraSource) throws IOException {
        if (cameraSource == null) {
            stop();
        }

        this.cameraSource = cameraSource;

        if (this.cameraSource != null) {
            startRequested = true;
            startIfReady();
        }
    }

    public void start(CameraSource cameraSource, GraphicOverlay overlay) throws IOException {
        this.overlay = overlay;
        start(cameraSource);
    }

    public void stop() {
        if (cameraSource != null) {
            cameraSource.stop();
        }
    }

    public void release() {
        if (cameraSource != null) {
            cameraSource.release();
            cameraSource = null;
        }
    }

    private void startIfReady() throws IOException {
        if (startRequested && surfaceAvailable) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            cameraSource.start(surfaceView.getHolder());
            if (overlay != null) {
                Size size = cameraSource.getPreviewSize();
                int min = Math.min(size.getWidth(), size.getHeight());
                int max = Math.max(size.getWidth(), size.getHeight());
                Log.e(TAG, "startIfReady::isPortraitMode() : " + isPortraitMode());
                if (isPortraitMode()) {
                    // Swap width and height sizes when in portrait, since it will be rotated by
                    // 90 degrees
                    overlay.setCameraInfo(min, max, cameraSource.getCameraFacing());
                } else {
                    overlay.setCameraInfo(max, min, cameraSource.getCameraFacing());
                }
                overlay.clear();
            }
            startRequested = false;
        }
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder surface) {
            surfaceAvailable = true;
            try {
                startIfReady();
            } catch (IOException e) {
                Log.e(TAG, "Could not start camera source.", e);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surface) {
            surfaceAvailable = false;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        setRatio();
    }

    public CameraSurfaceView getSurfaceView() {
        return surfaceView;
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

        // 이거 왜 넣은거지...?
//        if (cameraSource != null) {
//            Log.e("PREVIEW", "cameraSource != null : ");
//            Size size = cameraSource.getPreviewSize();
//            if (size != null) {
//                Log.e("PREVIEW", "size != null : ");
//                ratioWidth = size.getWidth();
//                ratioHeight = size.getHeight();
//            }
//        }

        // Swap width and height sizes when in portrait, since it will be rotated 90 degrees
        Log.e(TAG, "setRatio::isPortraitMode() : " + isPortraitMode());
        if (!isPortraitMode()) {
            int tmp = ratioWidth;
            ratioWidth = ratioHeight;
            ratioHeight = tmp;
        }

        float ratioVal = (float) ratioHeight / (float) ratioWidth;

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

    public CameraSource getCameraSource() {
        return cameraSource;
    }

    private boolean isPortraitMode() {
        int orientation = context.getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
