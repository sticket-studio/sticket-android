package com.sticket.app.sticket.util.camera_setting;

// 비율 관련 Enum
public enum Ratio {
    RATIO_3_4(0), RATIO_9_16(1), RATIO_1_1(2);

    private int val;

    Ratio(int val) {
        this.val = val;
    }

    public static Ratio toMyEnum(int val) {
        try {
            return values()[val];
        } catch (Exception ex) {
            // For error cases
            return RATIO_3_4;
        }
    }

    public int getVal() {
        return val;
    }
}