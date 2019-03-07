package com.sticket.app.sticket.util.camera_setting;

public enum Direction {
    DIRECTION_FRONT(0), DIRECTION_BACK(1);

    int val;

    Direction(int val){
        this.val =val;
    }

    public static Direction toMyEnum(int val) {
        try {
            return values()[val];
        } catch (Exception ex) {
            // For error cases
            return DIRECTION_FRONT;
        }
    }

    public int getVal() {
        return val;
    }
}
