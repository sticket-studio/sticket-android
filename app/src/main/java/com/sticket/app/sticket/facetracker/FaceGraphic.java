package com.sticket.app.sticket.facetracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.SticonAsset;
import com.sticket.app.sticket.util.BitmapUtils;
import com.sticket.app.sticket.util.ImageUtil;
import com.sticket.app.sticket.util.MyBitmapFactory;

import java.util.Map;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
public class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float FACE_POSITION_RADIUS = 10.0f;
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

    private Paint mFacePositionPaint;

    private volatile Face face;
    private int mFaceId;

    public FaceGraphic(GraphicOverlay overlay) {
        super(overlay);

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(selectedColor);
    }

    public void setId(int id) {
        mFaceId = id;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    public void updateFace(Face face) {
        this.face = face;
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(final Canvas canvas) {
        float halfFaceWidth = face.getWidth() / 2;
        float halfFaceHeight = face.getHeight() / 2;
        float x = translateX(face.getPosition().x + halfFaceWidth);
        float y = translateY(face.getPosition().y + halfFaceHeight);

        // Draws a bounding box around the face.
        float xOffset = scaleX(halfFaceWidth);
        float yOffset = scaleY(halfFaceHeight);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        float ratio = xOffset / 400;

        Map<SticonAsset, Asset> assetMap = MyBitmapFactory.getInstance().getAssetMap();

        for (Landmark landmark : face.getLandmarks()) {
            SticonAsset sticonAsset = null;

            for (SticonAsset s : assetMap.keySet()) {
                if (landmark.getType() == s.getLandmark().getNo()) {
                    sticonAsset = s;
                }
            }

            if (sticonAsset != null && landmark.getPosition() != null) {
                Asset asset = assetMap.get(sticonAsset);

                final int cx = (int) translateX(landmark.getPosition().x);
                final int cy = (int) translateY(landmark.getPosition().y);

//            if (LANDMARK_DRAWABLE_ARRAY[type] != NO_IMAGE) {
                Bitmap bitmap = BitmapFactory.decodeFile(asset.getLocalUrl());
                bitmap = BitmapUtils.rotate(bitmap, sticonAsset.getRotate());

                if (bitmap != null) {
                    drawLandMark(ratio * (float) sticonAsset.getRatio(), canvas, bitmap, cx, cy,
                            F_CENTER + (float) sticonAsset.getOffsetX(),
                            F_CENTER + (float) sticonAsset.getOffsetY(),
                            face.getEulerZ());
                    bitmap.recycle();
                }
//            } else {
//                if (landmark.getPosition() != null) {
//                    canvas.drawCircle(
//                            translateX(landmark.getPosition().x),
//                            translateY(landmark.getPosition().y),
//                            FACE_POSITION_RADIUS,
//                            mFacePositionPaint);
//                }
            }
        }

    }

    private void drawLandMark(float ratio, Canvas canvas, Bitmap icon, int cx, int cy, float xf, float yf, float degrees) {
        Bitmap rotatedBitmap = ImageUtil.rotateBitmap(icon, (int)degrees);
        float scaledWidth = ratio * icon.getWidth();
        float scaledHeight = ratio * icon.getHeight();
        int left = (int) (cx - scaledWidth * xf);
        int top = (int) (cy - scaledHeight * yf);
        canvas.drawBitmap(rotatedBitmap, null, getRect(ratio, cx, cy, rotatedBitmap, xf, yf), mFacePositionPaint);
        rotatedBitmap.recycle();
    }

    private Rect getRect(float ratio, int cx, int cy, Bitmap icon, float xf, float yf) {
        int scaledWidth = (int) (icon.getWidth() * ratio);
        int scaledHeight = (int) (icon.getHeight() * ratio);
        int left = (int) (cx - scaledWidth * xf);
        int top = (int) (cy - scaledHeight * yf);
        int right = (int) (cx + scaledWidth * (1 - xf));
        int bottom = (int) (cy + scaledHeight * (1 - yf));
        return new Rect(left, top, right, bottom);
    }
}
