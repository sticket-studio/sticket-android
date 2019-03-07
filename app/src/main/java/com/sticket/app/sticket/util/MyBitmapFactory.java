package com.sticket.app.sticket.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;
import com.sticket.app.sticket.R;

import java.util.ArrayList;
import java.util.List;

public class MyBitmapFactory {
    private static final int NO_IMAGE = -1;
    private static final int LANDMARK_SIZE = 12;
    private static final int[] LANDMARK_DRAWABLE_ARRAY = new int[]{
            R.drawable.mouth_bottom, R.drawable.cheek, NO_IMAGE
            , NO_IMAGE, R.drawable.left_eye, NO_IMAGE
            , R.drawable.nose, R.drawable.cheek, NO_IMAGE
            , NO_IMAGE, R.drawable.right_eye, NO_IMAGE};

    private List<Integer> landmarkIdxList;

    private static MyBitmapFactory instance;

    private Bitmap[] bitmaps;

    private MyBitmapFactory(){
    }

    public void build(Context context){
        initBitmaps(context);
        initLandMarkList();
    }

    public static MyBitmapFactory getInstance(){
        if (instance == null) {
            synchronized (MyBitmapFactory.class) {
                if (instance == null) {
                    instance = new MyBitmapFactory();
                }
            }
        }
        return instance;
    }

    private void initBitmaps(Context context) {
        bitmaps = new Bitmap[LANDMARK_SIZE];

        for (int i = 0; i < LANDMARK_SIZE; i++) {
            if (LANDMARK_DRAWABLE_ARRAY[i] != NO_IMAGE) {
                bitmaps[i] = BitmapFactory.decodeResource(context.getResources(), LANDMARK_DRAWABLE_ARRAY[i]);
            }
        }
    }

    private void initLandMarkList(){
        landmarkIdxList = new ArrayList<>();
        landmarkIdxList.add(FirebaseVisionFaceLandmark.MOUTH_BOTTOM);
        landmarkIdxList.add(FirebaseVisionFaceLandmark.MOUTH_RIGHT);
        landmarkIdxList.add(FirebaseVisionFaceLandmark.MOUTH_LEFT);
        landmarkIdxList.add(FirebaseVisionFaceLandmark.RIGHT_EYE);
        landmarkIdxList.add(FirebaseVisionFaceLandmark.LEFT_EYE);
        landmarkIdxList.add(FirebaseVisionFaceLandmark.RIGHT_EAR);
        landmarkIdxList.add(FirebaseVisionFaceLandmark.LEFT_EAR);
        landmarkIdxList.add(FirebaseVisionFaceLandmark.RIGHT_CHEEK);
        landmarkIdxList.add(FirebaseVisionFaceLandmark.LEFT_CHEEK);
        landmarkIdxList.add(FirebaseVisionFaceLandmark.NOSE_BASE);
    }

    public Bitmap[] getBitmaps() {
        return bitmaps;
    }

    public Bitmap getBitmap(int bitmapIdx){
        return bitmaps[bitmapIdx];
    }

    public List<Integer> getLandmarkIdxList() {
        return landmarkIdxList;
    }
}
