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

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.face.FaceDetector;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.gallery.SelectedPictureActivity;
import com.sticket.app.sticket.activities.setting.CameraSettingDialog;
import com.sticket.app.sticket.activities.setting.SettingActivity;
import com.sticket.app.sticket.activities.sticker.StickerDialog;
import com.sticket.app.sticket.activities.store.StoreActivity;
import com.sticket.app.sticket.database.DBTest;
import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.entity.Sticon;
import com.sticket.app.sticket.databinding.ActivityLivePreviewBinding;
import com.sticket.app.sticket.facedetection.FaceContourDetectorProcessor;
import com.sticket.app.sticket.facetracker.GraphicFaceTrackerFactory;
import com.sticket.app.sticket.facetracker.MyFaceDetector;
import com.sticket.app.sticket.util.Alert;
import com.sticket.app.sticket.util.CameraUtil;
import com.sticket.app.sticket.util.FileUtil;
import com.sticket.app.sticket.util.ImageUtil;
import com.sticket.app.sticket.util.MyBitmapFactory;
import com.sticket.app.sticket.util.PermissionUtil;
import com.sticket.app.sticket.util.Preference;
import com.sticket.app.sticket.util.camera_setting.CameraOption;
import com.sticket.app.sticket.util.camera_setting.Direction;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    public static final String IMG_FORMAT = ".jpg";

    private CameraSettingDialog cameraSettingDialog;
    private FaceContourDetectorProcessor faceContourDetectorProcessor;
    private SticketDatabase sticketDatabase;

    private ActivityLivePreviewBinding binding;

    //////////////////////

    private CameraSource cameraSource = null;

    private MyFaceDetector myFaceDetector;

    private int currentCameraDirection = CameraSource.CAMERA_FACING_FRONT;

    private static final int RC_HANDLE_GMS = 9001;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    private boolean isCapturing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_live_preview);

        sticketDatabase = SticketDatabase.getDatabase(getApplicationContext());

//        binding.previewPreview.setRatio();

        binding.btnPreviewFacingSwitch.setOnCheckedChangeListener(this);

        if (Camera.getNumberOfCameras() == 1) {
            binding.btnPreviewFacingSwitch.setVisibility(View.GONE);
        }

        if (PermissionUtil.allPermissionsGranted(this)) {
            createCameraSource();
        } else {
            PermissionUtil.getRuntimePermissions(this, PERMISSION_REQUESTS);
        }
        DBTest.patchAssetIfNotExist(this);

        initViews();
    }

    private void initViews() {
        cameraSettingDialog = new CameraSettingDialog(LivePreviewActivity.this);
        cameraSettingDialog.setOnRatioChangeListener((ratioVal) -> refreshPreview());
        cameraSettingDialog.setOnQualityChangeListener(isHighQuality -> refreshPreview());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (cameraSource != null) {
            Direction direction = isChecked ? Direction.DIRECTION_FRONT : Direction.DIRECTION_BACK;

            CameraOption.getInstance().setDirection(direction);
            Preference.getInstance().putInt(PREFERENCE_NAME_DIRECTION, direction.getVal());
        }
        binding.previewPreview.stop();
        startCameraSource();
    }

    private void createCameraSource() {
        int direction = Preference.getInstance().getInt(PREFERENCE_NAME_DIRECTION);
        Context context = getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
//                .setProminentFaceOnly(true)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory(binding.graphyOverlayPreview, this))
                        .build());
        CameraSource.Builder cb = new CameraSource.Builder(context, detector)
                .setFacing(direction)
                .setRequestedFps(30.0f)
                .setAutoFocusEnabled(true);
//        if (binding.previewPreview.getWidth() != 0 && binding.previewPreview.getHeight() != 0) {
//            cb.setRequestedPreviewSize(binding.previewPreview.getWidth(), binding.previewPreview.getHeight());
//        }
        cameraSource = cb.build();
    }

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {
        createCameraSource();
        if (cameraSource != null) {
            try {
                if (binding.previewPreview == null) {
                    Log.d(TAG, "resume: Preview is null");
                }
                if (binding.graphyOverlayPreview == null) {
                    Log.d(TAG, "resume: graphOverlay is null");
                }
                binding.previewPreview.start(cameraSource, binding.graphyOverlayPreview);
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
        binding.previewPreview.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int reqCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "Permission granted!");
        if (PermissionUtil.allPermissionsGranted(this)) {
            createCameraSource();
        }

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
            onCaptureBtnClick(null);
        } else {
            // TODO: 초점맞추기
        }
    }

    public void onCaptureBtnClick(View v) {
        if (!isCapturing) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        isCapturing = true;

                        for (int i = CameraOption.getInstance().getTimer().getVal(); i > 0; i--) {
                            final int finalI = i;

                            runOnUiThread(() -> {
                                binding.txtCountDown.setVisibility(View.VISIBLE);
                                binding.txtCountDown.setText(String.valueOf(finalI));
                            });

                            Thread.sleep(1000);
                        }

                        runOnUiThread(() -> {
                            binding.txtCountDown.setVisibility(View.GONE);

                            shutterEffect();
                        });

                        if (CameraOption.getInstance().isAutoSave()) {
                            capture();
                        } else {
                            runOnUiThread(() -> Alert.makeText("자동저장이 아님. 나중에 처리할 예정"));
                        }
                        isCapturing = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private void shutterEffect() {
        Animation shutterAnimation = AnimationUtils.loadAnimation(LivePreviewActivity.this, R.anim.shutter_effect);
        shutterAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.viewShutterEffect.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.viewShutterEffect.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        shutterAnimation.setFillEnabled(false);
        binding.viewShutterEffect.startAnimation(shutterAnimation);
    }

    private void capture() {
        cameraSource.takePicture(() -> {

        }, data -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            ImageUtil.store(this, bitmap);
        });

        Bitmap bitmap = binding.previewPreview.getmSurfaceView().drawBitmap();
//        ImageUtil.store(this, bitmap);
    }

    private void refreshPreview() {
        binding.previewPreview.release();
        binding.previewPreview.setRatio();
        startCameraSource();
    }
}