package com.sticket.app.sticket.util.camera_setting;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.util.Preference;

/**
 * 카메라 세팅값을 위한 Class
 * - Flash
 * - Timer
 */
public class CameraOption {
    public static final String PREFERENCE_NAME_DIRECTION = "DIRECTION";
    public static final String PREFERENCE_NAME_FLASH = "FLASH";
    public static final String PREFERENCE_NAME_RATIO = "RATIO";
    public static final String PREFERENCE_NAME_TIMER = "TIMER";
    public static final String PREFERENCE_NAME_AUTO_SAVE = "AUTO_SAVE";
    public static final String PREFERENCE_NAME_TOUCH_CAPTURE = "TOUCH_CAPTURE";
    public static final String PREFERENCE_NAME_HIGH_QUALITY = "HIGH_QUALITY";

    public static final int[] RATIO_IMGS = new int[]{R.drawable.img_ratio_3_4
            , R.drawable.img_ratio_9_16, R.drawable.img_ratio_1_1};

    public static final int[] TIMER_IMGS = new int[]{R.drawable.img_timer_none
            , R.drawable.img_timer_sec_3, R.drawable.img_timer_sec_5, R.drawable.img_timer_sec_7};

    private static CameraOption instance;

    private Direction direction;
    private Flash flash;
    private Ratio ratio;
    private Timer timer;
    private boolean autoSave;
    private boolean touchCapture;
    private boolean highQuality;

    private CameraOption() {
        direction = Direction.toMyEnum(Preference.getInstance().getInt(PREFERENCE_NAME_DIRECTION));
        flash = Flash.toMyEnum(Preference.getInstance().getInt(PREFERENCE_NAME_FLASH));
        ratio = Ratio.toMyEnum(Preference.getInstance().getInt(PREFERENCE_NAME_RATIO));
        timer = Timer.toMyEnum(Preference.getInstance().getInt(PREFERENCE_NAME_TIMER));
    }

    public static CameraOption getInstance() {
        if (instance == null) {
            instance = new CameraOption();
        }
        return instance;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Flash getFlash() {
        return flash;
    }

    public void setFlash(Flash flash) {
        this.flash = flash;
    }

    public Ratio getRatio() {
        return ratio;
    }

    public void setRatio(Ratio ratio) {
        this.ratio = ratio;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
