package com.sticket.app.sticket.util.camera_setting;

// 비율 관련 Enum
public enum Ratio {
    RATIO_3_4(0, 3, 4), RATIO_9_16(1, 9, 16), RATIO_1_1(2, 1, 1);

    private int val;
    private int width;
    private int height;

    Ratio(int val, int width, int height) {
        this.val = val;
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}