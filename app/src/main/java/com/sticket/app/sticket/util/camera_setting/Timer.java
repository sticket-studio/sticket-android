package com.sticket.app.sticket.util.camera_setting;

// 타이머 관련 Enum
public enum Timer {
    TIMER_NONE(0), TIMER_SEC3(1), TIMER_SEC5(2), TIMER_SEC7(3);

    private int val;

    Timer(int val) {
        this.val = val;
    }

    public static Timer toMyEnum(int val) {
        try {
            return values()[val];
        } catch (Exception ex) {
            // For error cases
            return TIMER_NONE;
        }
    }

    public int getVal() {
        return val;
    }
}