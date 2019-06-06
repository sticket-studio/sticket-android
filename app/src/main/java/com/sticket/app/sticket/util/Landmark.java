package com.sticket.app.sticket.util;

public enum Landmark {
    EYE_LEFT(30.4f, 42.3f), EYE_RIGHT(64.6f, 42.3f), NOSE(45.6f, 52.9f),
    MOUTH(49.4f, 66.1f), CHEEK_LEFT(24.7f, 58.2f), CHEEK_RIGHT(72.2f, 58.2f),
    GLASSES(47.5f, 42.3f);
//    EYE_LEFT(-19.6f, -7.7f), EYE_RIGHT(-14.6f, -7.7f), NOSE(-4.4f, 2.9f),
//    MOUTH(-0.6f, 16.1f), CHEEK_LEFT(-25.3f, 8.2f), CHEEK_RIGHT(-17.8f, 8.2f),
//    GLASSES(-2.5f, -7.7f);

    private float x;
    private float y;

    Landmark(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
