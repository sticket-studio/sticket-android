// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.sticket.app.sticket;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sticket.app.sticket.common.CameraSource;
import com.sticket.app.sticket.common.CameraSourcePreview;
import com.sticket.app.sticket.common.GraphicOverlay;
import com.sticket.app.sticket.facedetection.FaceContourDetectorProcessor;
import com.sticket.app.sticket.util.Alert;
import com.sticket.app.sticket.util.CameraSettingDialog;
import com.sticket.app.sticket.util.Preference;
import com.sticket.app.sticket.util.SettingActivity;
import com.sticket.app.sticket.util.StickerDialog;
import com.sticket.app.sticket.util.camera_setting.CameraOption;
import com.sticket.app.sticket.util.camera_setting.Direction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo app showing the various features of ML Kit for Firebase. This class is used to
 * set up continuous frame processing on frames from a camera source.
 */
public final class LivePreviewActivity extends AppCompatActivity
        implements OnRequestPermissionsResultCallback,
        CompoundButton.OnCheckedChangeListener {
    private static final String FACE_CONTOUR = "Face Contour";
    private static final String TAG = "LivePreviewActivity";
    private static final int PERMISSION_REQUESTS = 1;

    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;
    public static Context mContext;
    private CameraSettingDialog cameraSettingDialog;

    private TextView countDownTxt;
    private FaceContourDetectorProcessor faceContourDetectorProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_live_preview);

        preview = findViewById(R.id.firePreview);
        if (preview == null) {
            Log.d(TAG, "Preview is null");
        }
        graphicOverlay = findViewById(R.id.fireFaceOverlay);
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }

        ToggleButton facingSwitch = findViewById(R.id.btnSwitch);
        facingSwitch.setOnCheckedChangeListener(this);

        // Hide the toggle button if there is only 1 camera
        if (Camera.getNumberOfCameras() == 1) {
            facingSwitch.setVisibility(View.GONE);
        }

        if (allPermissionsGranted()) {
            createCameraSource();
        } else {
            getRuntimePermissions();
        }

        mContext = this;

        initViews();
    }

    private void initViews() {
        countDownTxt = findViewById(R.id.txtCountDown);
        cameraSettingDialog = new CameraSettingDialog(LivePreviewActivity.this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (cameraSource != null) {
            Direction direction;
            if (isChecked) {
                cameraSource.setFacing(CameraSource.CAMERA_FACING_FRONT);
                direction = Direction.DIRECTION_FRONT;
            } else {
                cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
                direction = Direction.DIRECTION_BACK;
            }

            CameraOption.getInstance().setDirection(direction);
            Preference.getInstance().putInt(CameraOption.PREFERENCE_NAME_DIRECTION
                    , direction.getVal());
        }
        preview.stop();
        startCameraSource();
    }

    private void createCameraSource() {
        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
            int directionInt = Preference.getInstance()
                    .getInt(CameraOption.PREFERENCE_NAME_DIRECTION);
            if (Direction.toMyEnum(directionInt) == Direction.DIRECTION_BACK) {
                cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
            } else {
                cameraSource.setFacing(CameraSource.CAMERA_FACING_FRONT);
            }
        }

        Log.i(TAG, "Using Face Contour Detector Processor");
        faceContourDetectorProcessor = new FaceContourDetectorProcessor(LivePreviewActivity.this);
        cameraSource.setMachineLearningFrameProcessor(faceContourDetectorProcessor);
    }

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "resume: Preview is null");
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null");
                }
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    private String[] getRequiredPermissions() {
        try {
            PackageInfo info =
                    this.getPackageManager()
                            .getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] ps = info.requestedPermissions;
            if (ps != null && ps.length > 0) {
                return ps;
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            return new String[0];
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false;
            }
        }
        return true;
    }

    private void getRuntimePermissions() {
        List<String> allNeededPermissions = new ArrayList<>();
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                allNeededPermissions.add(permission);
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        Log.i(TAG, "Permission granted!");
        if (allPermissionsGranted()) {
            createCameraSource();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission granted: " + permission);
            return true;
        }
        Log.i(TAG, "Permission NOT granted: " + permission);
        return false;
    }

    public void btnSticker(View v) {
        StickerDialog stickerDialog = new StickerDialog(LivePreviewActivity.this);
        stickerDialog.openDialog();
    }

    public void btnCameraSetting(View v) {
        cameraSettingDialog.setCamera(cameraSource);
        cameraSettingDialog.show();
    }

    public void btnSetting(View v) {
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(intent);
    }

    public void onTouchPreview(View v) {
        if (CameraOption.getInstance().isTouchCapture()) {
            btnCapture(null);
        } else {
            // 머하징
        }
    }

    boolean isCapturing;

    public void btnCapture(View v) {
        if (!isCapturing) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        super.run();
                        isCapturing = true;
                        int sec = 0;
                        switch (CameraOption.getInstance().getTimer()) {
                            case TIMER_NONE:
                                break;
                            case TIMER_SEC3:
                                sec = 3;
                                break;
                            case TIMER_SEC5:
                                sec = 5;
                                break;
                            case TIMER_SEC7:
                                sec = 7;
                                break;
                        }

                        for (int i = sec; i > 0; i--) {
                            final int finalI = i;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    countDownTxt.setVisibility(View.VISIBLE);
                                    countDownTxt.setText(String.valueOf(finalI));
                                }
                            });

                            Thread.sleep(1000);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Alert.makeText("Cheeeeeze!");
                                countDownTxt.setVisibility(View.GONE);
                            }
                        });

                        if (CameraOption.getInstance().isAutoSave()) {
                            faceContourDetectorProcessor.capture();
                        } else {
                            Alert.makeText("자동저장이 아님.나중에 처리할 예정");
                        }
                        isCapturing = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}