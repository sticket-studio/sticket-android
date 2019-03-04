package com.sticket.app.sticket.util;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.util.camera_setting.CameraOption;
import com.sticket.app.sticket.util.camera_setting.Flash;
import com.sticket.app.sticket.util.camera_setting.Ratio;
import com.sticket.app.sticket.util.camera_setting.Timer;

import static com.sticket.app.sticket.util.camera_setting.CameraOption.PREFERENCE_NAME_FLASH;
import static com.sticket.app.sticket.util.camera_setting.CameraOption.PREFERENCE_NAME_RATIO;
import static com.sticket.app.sticket.util.camera_setting.CameraOption.PREFERENCE_NAME_TIMER;

public class CameraSettingDialog extends Dialog {

    private ToggleButton flashToggleBtn;
    private Button timerBtn, ratioBtn;

    private Context context;

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;
    private View.OnClickListener onClickListener = null;

    public CameraSettingDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_camera_setting);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.TOP);
        params.y = 200;   //y position
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        window.setAttributes(params);


        initListeners();
        initViews();
    }

    // view에서 갖다 쓰기 때문에 initViews() 보다 먼저 해줘야함!
    private void initListeners() {
        onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.toggleFlash:
                        if (isChecked) {
                            // Flash 조작 불가능 하다면 다시 OFF 처리
                            if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                                Alert.makeText("플래시 조작이 불가능합니다");

                                buttonView.setChecked(false);

                                // SharedPreference에 Flash값 저장 - FLASH_OFF
                                CameraOption.getInstance().setFlash(Flash.FLASH_OFF);
                            } else {
                                Alert.makeText("플래시 On");

                                CameraOption.getInstance().setFlash(Flash.FLASH_ON);
                            }
                        } else {
                            Alert.makeText("플래시 Off");

                            CameraOption.getInstance().setFlash(Flash.FLASH_OFF);
                        }

                        Preference.getInstance().putInt(PREFERENCE_NAME_FLASH
                                , CameraOption.getInstance().getFlash().getVal());
                        break;
                }
            }
        };

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnTimer:
                        Timer curTimer = CameraOption.getInstance().getTimer();
                        int curTimerValue = curTimer.getVal();
                        int nextTimerIdx = (curTimerValue + 1) % Timer.values().length;

                        Timer nextTimer = Timer.values()[nextTimerIdx];
                        CameraOption.getInstance().setTimer(nextTimer);

                        Preference.getInstance().putInt(PREFERENCE_NAME_TIMER
                                , nextTimer.getVal());
                        timerBtn.setBackgroundResource(CameraOption.TIMER_IMGS[nextTimer.getVal()]);

                        Alert.makeText(nextTimer.name());
                        break;
                }
                switch (v.getId()) {
                    case R.id.btnRatio:
                        Ratio curTimer = CameraOption.getInstance().getRatio();
                        int curRatioValue = curTimer.getVal();
                        int nextRatioIdx = (curRatioValue + 1) % Ratio.values().length;

                        Ratio nextRatio = Ratio.values()[nextRatioIdx];
                        CameraOption.getInstance().setRatio(nextRatio);

                        Preference.getInstance().putInt(PREFERENCE_NAME_RATIO
                                , nextRatio.getVal());
                        ratioBtn.setBackgroundResource(CameraOption.RATIO_IMGS[nextRatio.getVal()]);

                        Alert.makeText(nextRatio.name());
                        break;
                }
            }
        };
    }

    private void initViews() {
        flashToggleBtn = findViewById(R.id.toggleFlash);
        ratioBtn = findViewById(R.id.btnRatio);
        timerBtn = findViewById(R.id.btnTimer);

        int savedFlashVal = Preference.getInstance().getInt(CameraOption.PREFERENCE_NAME_FLASH);
        int savedRatioVal = Preference.getInstance().getInt(CameraOption.PREFERENCE_NAME_RATIO);
        int savedTimerVal = Preference.getInstance().getInt(CameraOption.PREFERENCE_NAME_TIMER);

        Flash savedFlash = Flash.toMyEnum(savedFlashVal);

        boolean flashOn = savedFlash.equals(Flash.FLASH_ON);

        flashToggleBtn.setChecked(flashOn);
        timerBtn.setBackgroundResource(CameraOption.TIMER_IMGS[savedTimerVal]);
        ratioBtn.setBackgroundResource(CameraOption.RATIO_IMGS[savedRatioVal]);

        if (onCheckedChangeListener == null) {
            throw new RuntimeException("OnCheckedListener is NULL");
        } else {
            flashToggleBtn.setOnCheckedChangeListener(onCheckedChangeListener);
        }

        if (onClickListener == null) {
            throw new RuntimeException("OnClickListener is NULL");
        } else {
            ratioBtn.setOnClickListener(onClickListener);
            timerBtn.setOnClickListener(onClickListener);
        }
    }
}
