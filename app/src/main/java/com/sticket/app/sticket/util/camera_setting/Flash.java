package com.sticket.app.sticket.util.camera_setting;

// 플래시 관련 Enum
public enum Flash {
    FLASH_OFF(0), FLASH_AUTO(1), FLASH_ON(2);

    private int val;

    Flash(int val) {
        this.val = val;
    }

    public static Flash toMyEnum(int val) {
        try {
            return values()[val];
        } catch (Exception ex) {
            // For error cases
            return FLASH_OFF;
        }
    }

    public int getVal() {
        return val;
    }
}