package com.sticket.app.sticket.util;

import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

import java.io.Serializable;

import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.LEFT_CHEEK;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.LEFT_EAR;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.LEFT_EYE;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.NOSE_BASE;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.RIGHT_CHEEK;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.RIGHT_EAR;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.RIGHT_EYE;

public enum Landmark implements Serializable {
    EYE_LEFT(33.038022813f, 42.8571428f, RIGHT_EYE, "왼눈"),
    EYE_RIGHT(62.01901140f, 42.8571428f, LEFT_EYE, "오른눈"),
//    GLASSES(47.5f, 42.8571428f, -1, "안경"),
    NOSE(47.6f, 52.9f, NOSE_BASE, "코"),
    MOUTH_BOTTOM(49.4f, 66.1f, FirebaseVisionFaceLandmark.MOUTH_BOTTOM, "입"),
    CHEEK_LEFT(27.7f, 58.2f, RIGHT_CHEEK, "왼볼"),
    CHEEK_RIGHT(71.2f, 58.2f, LEFT_CHEEK, "오른볼"),
    EAR_LEFT(11.95455f, 47.61905f, RIGHT_EAR, "왼귀"),
    EAR_RIGHT(90.49430f, 47.61905f, LEFT_EAR, "오른귀");

    public static final Landmark[] LANDMARKS = {EYE_LEFT, NOSE, MOUTH_BOTTOM, CHEEK_LEFT, EAR_LEFT};

    private final float x;
    private final float y;
    private final int no;
    private final String korName;

    Landmark(float x, float y, int no, String korName) {

        this.x = x;
        this.y = y;
        this.no = no;
        this.korName = korName;
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

    public String getKorName() {
        return korName;
    }
}
