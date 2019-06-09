package com.sticket.app.sticket.facedetection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;
import com.sticket.app.sticket.common.GraphicOverlay;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.SticonAsset;
import com.sticket.app.sticket.util.BitmapUtils;
import com.sticket.app.sticket.util.MyBitmapFactory;

import java.util.Map;

/**
 * Graphic instance for rendering face contours graphic overlay view.
 */
public class FaceContourGraphic extends GraphicOverlay.Graphic {

    private static final float FACE_POSITION_RADIUS = 4.0f;
    private static final float ID_TEXT_SIZE = 30.0f;
    private static final float ID_Y_OFFSET = 80.0f;
    private static final float ID_X_OFFSET = -70.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private static final float F_CENTER = 0.5f;

    private final Paint facePositionPaint;
    private final Paint idPaint;
    private final Paint boxPaint;

    private volatile FirebaseVisionFace firebaseVisionFace;

    public FaceContourGraphic(GraphicOverlay overlay, FirebaseVisionFace face) {
        super(overlay);

        this.firebaseVisionFace = face;
        final int selectedColor = Color.WHITE;

        facePositionPaint = new Paint();
        facePositionPaint.setColor(selectedColor);

        idPaint = new Paint();
        idPaint.setColor(selectedColor);
        idPaint.setTextSize(ID_TEXT_SIZE);

        boxPaint = new Paint();
        boxPaint.setColor(selectedColor);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        FirebaseVisionFace face = firebaseVisionFace;
        if (face == null) {
            return;
        }

        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getBoundingBox().centerX());
        float y = translateY(face.getBoundingBox().centerY());

        // Draws a bounding box around the face.
        float xOffset = scaleX(face.getBoundingBox().width() / 2.0f);
        float yOffset = scaleY(face.getBoundingBox().height() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        float ratio = xOffset / 400;
        canvas.drawRect(left, top, right, bottom, boxPaint);

        FirebaseVisionFaceContour contour = face.getContour(FirebaseVisionFaceContour.ALL_POINTS);
        for (com.google.firebase.ml.vision.common.FirebaseVisionPoint point : contour.getPoints()) {
            float px = translateX(point.getX());
            float py = translateY(point.getY());
            canvas.drawCircle(px, py, FACE_POSITION_RADIUS, facePositionPaint);
        }

        Map<SticonAsset, Asset> assetMap = MyBitmapFactory.getInstance().getAssetMap();

        for (SticonAsset sticonAsset : assetMap.keySet()) {
            Asset asset = assetMap.get(sticonAsset);

            FirebaseVisionFaceLandmark landmark = face.getLandmark(sticonAsset.getLandmark().getNo());
            if (landmark != null && landmark.getPosition() != null) {

                final int cx = (int) translateX(landmark.getPosition().getX());
                final int cy = (int) translateY(landmark.getPosition().getY());

                BitmapFactory.Options options = new BitmapFactory.Options();

//            if (LANDMARK_DRAWABLE_ARRAY[type] != NO_IMAGE) {
                Bitmap bitmap = BitmapFactory.decodeFile(asset.getLocal_url());
                bitmap = BitmapUtils.rotate(bitmap, sticonAsset.getRotate());

                if (bitmap != null) {
                    drawLandMark(ratio * (float) sticonAsset.getRatio(), canvas, bitmap, cx, cy,
                            F_CENTER + (float) sticonAsset.getOffsetX(),
                            F_CENTER + (float) sticonAsset.getOffsetY());
                } else {
//                    canvas.drawCircle(
//                            translateX(cx),
//                            translateY(cy),
//                            FACE_POSITION_RADIUS,
//                            facePositionPaint);
                }
            }
        }
    }

//    private void printSticon(){
//
//    }
//
//    private void printSticon(){
//
//    }

    private void drawLandMark(float ratio, Canvas canvas, Bitmap icon, int cx, int cy) {
        canvas.drawBitmap(icon, null, getRect(ratio, cx, cy, icon, F_CENTER, F_CENTER), facePositionPaint);
    }

    private void drawLandMark(float ratio, Canvas canvas, Bitmap icon, int cx, int cy, float xf, float yf) {
        canvas.drawBitmap(icon, null, getRect(ratio, cx, cy, icon, xf, yf), facePositionPaint);
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
