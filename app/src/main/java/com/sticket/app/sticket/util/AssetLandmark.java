package com.sticket.app.sticket.util;

import java.io.Serializable;

import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.LEFT_CHEEK;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.LEFT_EAR;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.LEFT_EYE;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.MOUTH_BOTTOM;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.NOSE_BASE;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.RIGHT_CHEEK;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.RIGHT_EAR;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.RIGHT_EYE;

public enum AssetLandmark implements Serializable {
    EYE, NOSE, MOUTH_BOTTOM, CHEEK, EAR
}