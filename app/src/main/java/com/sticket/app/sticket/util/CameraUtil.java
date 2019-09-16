package com.sticket.app.sticket.util;

import android.content.Context;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.vision.CameraSource;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.camera.LivePreviewActivity;

import java.lang.reflect.Field;

public class CameraUtil {
    public static Camera getCamera(@NonNull CameraSource cameraSource) {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    Camera camera = (Camera) field.get(cameraSource);
                    if (camera != null) {
                        return camera;
                    }

                    return null;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

        return null;
    }

    public static void shutterEffect(Context context, View viewShutterEffect) {
        Animation shutterAnimation = AnimationUtils.loadAnimation(context, R.anim.shutter_effect);
        shutterAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewShutterEffect.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewShutterEffect.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        shutterAnimation.setFillEnabled(false);
        viewShutterEffect.startAnimation(shutterAnimation);
    }
}
