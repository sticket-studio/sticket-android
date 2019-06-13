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
package com.sticket.app.sticket.activities.camera;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.gallery.SelectedPictureActivity;
import com.sticket.app.sticket.activities.setting.CameraSettingDialog;
import com.sticket.app.sticket.activities.setting.SettingActivity;
import com.sticket.app.sticket.activities.sticker.StickerDialog;
import com.sticket.app.sticket.activities.store.StoreActivity;
import com.sticket.app.sticket.common.CameraSource;
import com.sticket.app.sticket.common.CameraSourcePreview;
import com.sticket.app.sticket.common.GraphicOverlay;
import com.sticket.app.sticket.database.DBTest;
import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.entity.Sticon;
import com.sticket.app.sticket.facedetection.FaceContourDetectorProcessor;
import com.sticket.app.sticket.util.Alert;
import com.sticket.app.sticket.util.MyBitmapFactory;
import com.sticket.app.sticket.util.PermissionUtil;
import com.sticket.app.sticket.util.Preference;
import com.sticket.app.sticket.util.camera_setting.CameraOption;
import com.sticket.app.sticket.util.camera_setting.Direction;

import java.io.IOException;

import butterknife.BindView;

import static com.sticket.app.sticket.util.Preference.PREFERENCE_NAME_DIRECTION;

/**
 * Demo app showing the various features of ML Kit for Firebase. This class is used to
 * set up continuous frame processing on frames from a camera source.
 */
public final class LivePreviewActivity extends AppCompatActivity
        implements OnRequestPermissionsResultCallback,
        CompoundButton.OnCheckedChangeListener {
    private static final String TAG = LivePreviewActivity.class.getSimpleName();
    private static final int PERMISSION_REQUESTS = 1;

    @BindView(R.id.firePreview)
    private CameraSourcePreview preview;
    @BindView(R.id.fireFaceOverlay)
    private GraphicOverlay graphicOverlay;
    @BindView(R.id.btnSwitch)
    ToggleButton facingSwitch;
    @BindView(R.id.txtCountDown)
    private TextView countDownTxt;

    private CameraSource cameraSource = null;
    private CameraSettingDialog cameraSettingDialog;
    private FaceContourDetectorProcessor faceContourDetectorProcessor;
    private SticketDatabase sticketDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_preview);

        sticketDatabase = SticketDatabase.getDatabase(getApplicationContext());

        preview.setRatio();

        facingSwitch.setOnCheckedChangeListener(this);

        if (Camera.getNumberOfCameras() == 1) {
            facingSwitch.setVisibility(View.GONE);
        }

        if (PermissionUtil.allPermissionsGranted(this)) {
            createCameraSource();
        } else {
            PermissionUtil.getRuntimePermissions(this, PERMISSION_REQUESTS);
        }

        initViews();
    }

    private void initViews() {
        countDownTxt = findViewById(R.id.txtCountDown);       // Annotation in activity_live_preview
        cameraSettingDialog = new CameraSettingDialog(LivePreviewActivity.this);
        cameraSettingDialog.setOnRatioChangeListener(new CameraSettingDialog.OnRatioChangeListener() {
            @Override
            public void onRatioChange(int ratioVal) {
                preview.release();
                preview.setRatio();
                startCameraSource();
            }
        });
        cameraSettingDialog.setOnQualityChangeListener(new CameraSettingDialog.OnQualityChangeListener() {
            @Override
            public void onQualityChange(boolean isHighQuality) {
                preview.release();
                preview.setRatio();
                startCameraSource();
            }
        });
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
            Preference.getInstance().putInt(PREFERENCE_NAME_DIRECTION
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
                    .getInt(PREFERENCE_NAME_DIRECTION);
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

        Sticon sticon = sticketDatabase.sticonDao().getLastSticon();
        if (sticon != null) {
            MyBitmapFactory.getInstance().setSticon(sticketDatabase.sticonDao().getLastSticon());
        }

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

    //TODO : PermissionUtil로 따로 빼자
    @Override
    public void onRequestPermissionsResult(int reqCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "Permission granted!");
        if (PermissionUtil.allPermissionsGranted(this)) {
            createCameraSource();
        }

        // Asset insert test
        DBTest.patchAssetIfNotExist(this);

        super.onRequestPermissionsResult(reqCode, permissions, grantResults);
    }

    public void btnSticker(View v) {
        StickerDialog stickerDialog = new StickerDialog();
        stickerDialog.show(getSupportFragmentManager(), "BottomSheetDialog");
    }

    public void btnCameraSetting(View v) {
        cameraSettingDialog.setCamera(cameraSource);
        cameraSettingDialog.show();
    }

    public void btnSetting(View v) {
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(intent);
    }

    public void btnStore(View view) {
        Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
        startActivity(intent);
    }

    public void btnOpenGallery(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Get Album"), SelectedPictureActivity.REQUEST_CODE_FOR_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SelectedPictureActivity.REQUEST_CODE_FOR_GALLERY) {
            if (resultCode == RESULT_OK) {
                try {
                    Intent in1 = new Intent(this, SelectedPictureActivity.class);
                    in1.putExtra(SelectedPictureActivity.SELECTED_IMAGE_PATH, data.getData());
                    startActivity(in1);

                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public void onTouchPreview(View v) {
        if (CameraOption.getInstance().isTouchCapture()) {
            btnCapture(null);
        } else {
            // TODO: 초점맞추기
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
//                                Alert.makeText("Cheeeeeze!");
                                countDownTxt.setVisibility(View.GONE);

                                final View viewShutterEffect = findViewById(R.id.viewShutterEffect);
                                Animation shutterAnimation = AnimationUtils.loadAnimation(LivePreviewActivity.this, R.anim.shutter_effect);
                                shutterAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        viewShutterEffect.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        viewShutterEffect.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                shutterAnimation.setFillEnabled(false);
                                viewShutterEffect.startAnimation(shutterAnimation);
                            }
                        });

                        if (CameraOption.getInstance().isAutoSave()) {
                            faceContourDetectorProcessor.capture();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Alert.makeText("자동저장이 아님.나중에 처리할 예정");
                                }
                            });
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