package com.sticket.app.sticket.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {
    private static final String TAG = PermissionUtil.class.getSimpleName();

    public static final int REQ_READ_CONTACTS = 103;

    public static final String[] REQUIRED_PERMISSIONS = {

    };

    public static String[] getRequiredPermissions(Context context) {
        try {
            PackageInfo info = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] ps = info.requestedPermissions;
            if (ps != null && ps.length > 0) {
                return ps;
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            return new String[0];
        }
    }

    public static boolean allPermissionsGranted(Context context) {
        for (String permission : PermissionUtil.getRequiredPermissions(context)) {
            if (!isPermissionGranted(context, permission)) {
                return false;
            }
        }
        return true;
    }

    public static void getRuntimePermissions(Activity activity, int PERMISSION_REQUESTS) {
        List<String> allNeededPermissions = new ArrayList<>();
        for (String permission : PermissionUtil.getRequiredPermissions(activity)) {
            if (!isPermissionGranted(activity, permission)) {
                allNeededPermissions.add(permission);
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                    activity, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
        }
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission granted: " + permission);
            return true;
        }
        Log.i(TAG, "Permission NOT granted: " + permission);
        return false;
    }

    /**
     * @param context
     * @param permission ex) Manifest.permission.READ_CONTACTS
     * @return
     */
    public static boolean hasPermission(Context context, String permission) {
        int perm = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @param activity
     * @param permission ex) Manifest.permission.READ_CONTACTS
     * @param reqCode    ex) PermissionUtil.REQ_READ_CONTACTS
     */
    public static void requestPermission(Activity activity, String permission, int reqCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, reqCode);
    }

    /**
     * @param activity
     * @param permissions ex) Manifest.permission.READ_CONTACTS
     * @param reqCode
     */
    public static void requestPermissions(Activity activity, String[] permissions, int reqCode) {
        ActivityCompat.requestPermissions(activity, permissions, reqCode);
    }
}
