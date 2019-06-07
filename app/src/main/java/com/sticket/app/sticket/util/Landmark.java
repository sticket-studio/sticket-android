package com.sticket.app.sticket.util;

import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.*;

import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.LEFT_CHEEK;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.LEFT_EYE;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.MOUTH_BOTTOM;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.NOSE_BASE;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.RIGHT_CHEEK;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.RIGHT_EYE;

public enum Landmark {
    EYE_LEFT(30.4f, 42.3f, LEFT_EYE),
    EYE_RIGHT(64.6f, 42.3f, RIGHT_EYE),
    NOSE(45.6f, 52.9f, NOSE_BASE),
    MOUTH(49.4f, 66.1f, MOUTH_BOTTOM),
    CHEEK_LEFT(24.7f, 58.2f, LEFT_CHEEK),
    CHEEK_RIGHT(72.2f, 58.2f, RIGHT_CHEEK),
    GLASSES(47.5f, 42.3f, -1);
//    EYE_LEFT(-19.6f, -7.7f), EYE_RIGHT(-14.6f, -7.7f), NOSE(-4.4f, 2.9f),
//    MOUTH(-0.6f, 16.1f), CHEEK_LEFT(-25.3f, 8.2f), CHEEK_RIGHT(-17.8f, 8.2f),
//    GLASSES(-2.5f, -7.7f);

    private final float x;
    private final float y;
    private final int no;

    Landmark(float x, float y, int no) {
        this.x = x;
        this.y = y;
        this.no = no;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getNo() {
        return no;
    }
}
