/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.face.facetracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.google.android.gms.samples.vision.face.facetracker.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float FACE_POSITION_RADIUS = 10.0f;
    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private static final float F_CENTER = 0.5f;

    private static final int COLOR_CHOICES[] = {
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.RED,
            Color.WHITE,
            Color.YELLOW
    };
    private static int mCurrentColorIndex = 0;

    private Context context;

    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;

    private volatile Face mFace;
    private int mFaceId;
    private float mFaceHappiness;

    FaceGraphic(GraphicOverlay overlay, Context context) {
        super(overlay);

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(selectedColor);

        mIdPaint = new Paint();
        mIdPaint.setColor(selectedColor);
        mIdPaint.setTextSize(ID_TEXT_SIZE);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(selectedColor);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);

        this.context = context;
    }

    void setId(int id) {
        mFaceId = id;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(final Canvas canvas) {
        for (Landmark landmark : mFace.getLandmarks()) {
            int type = landmark.getType();

            final int cx = (int) translateX(landmark.getPosition().x);
            final int cy = (int) translateY(landmark.getPosition().y);
            switch (type) {
                case Landmark.LEFT_EYE:
                    drawLandMark(canvas, R.drawable.left_eye, cx, cy);
                    break;
                case Landmark.RIGHT_EYE:
                    drawLandMark(canvas, R.drawable.right_eye, cx, cy);
                    break;
                case Landmark.NOSE_BASE:
                    drawLandMark(canvas, R.drawable.nose, cx, cy);
                    break;
                case Landmark.BOTTOM_MOUTH:
                    drawLandMark(canvas, R.drawable.mouth_bottom, cx, cy, F_CENTER, 0.2f);
                    break;
                case Landmark.LEFT_CHEEK:
                    drawLandMark(canvas, R.drawable.cheek, cx, cy);
                    break;
                case Landmark.RIGHT_CHEEK:
                    drawLandMark(canvas, R.drawable.cheek, cx, cy);
                    break;
                default:
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(5);
                    canvas.drawCircle(cx, cy, 10, paint);
                    break;
            }
        }

        float halfFaceWidth = mFace.getWidth() / 2;
        float halfFaceHeight = mFace.getHeight() / 2;
        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(mFace.getPosition().x + halfFaceWidth);
        float y = translateY(mFace.getPosition().y + halfFaceHeight);
//        canvas.drawCircle(x, y, FACE_POSITION_RADIUS, mFacePositionPaint);
        canvas.drawText("id: " + mFaceId, x + ID_X_OFFSET, y + ID_Y_OFFSET, mIdPaint);
        canvas.drawText("happiness: " + String.format("%.2f", mFace.getIsSmilingProbability()), x - ID_X_OFFSET, y - ID_Y_OFFSET, mIdPaint);
//        canvas.drawText("right eye: " + String.format("%.2f", face.getIsRightEyeOpenProbability()), x + ID_X_OFFSET * 2, y + ID_Y_OFFSET * 2, mIdPaint);
//        canvas.drawText("left eye: " + String.format("%.2f", face.getIsLeftEyeOpenProbability()), x - ID_X_OFFSET*2, y - ID_Y_OFFSET*2, mIdPaint);

        // Draws a bounding box around the face.
        float xOffset = scaleX(halfFaceWidth);
        float yOffset = scaleY(halfFaceHeight);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        canvas.drawRect(left, top, right, bottom, mBoxPaint);
    }

    private void drawLandMark(Canvas canvas, int resId, int cx, int cy) {
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), resId);
        canvas.drawBitmap(icon, null, getRect(cx, cy, icon, F_CENTER, F_CENTER), new Paint());
    }

    private void drawLandMark(Canvas canvas, int resId, int cx, int cy, float xf, float yf) {
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), resId);
        canvas.drawBitmap(icon, null, getRect(cx, cy, icon, xf, yf), new Paint());
    }

    private Rect getRect(int cx, int cy, Bitmap icon, float xf, float yf) {
        float ratio = mFace.getWidth() / 200;
        int scaledWidth = (int) (icon.getWidth() * ratio);
        int scaledHeight = (int) (icon.getHeight() * ratio);
        int left = (int) (cx - scaledWidth * xf);
        int top = (int) (cy - scaledHeight * yf);
        int right = (int) (cx + scaledWidth * (1 - xf));
        int bottom = (int) (cy + scaledHeight * (1 - yf));
        return new Rect(left, top, right, bottom);
    }
}
