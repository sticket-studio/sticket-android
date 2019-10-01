package com.sticket.app.sticket.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AlertDialogBuilderUtil {
    public static void simpleDialog(Context context, String title, String message,
                                    final SimpleListener simpleListener) {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        simpleListener.positiveListener(dialog, which);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertBuilder.create().show();
    }

    public static void editTextDialog(Context context, String title, String message,
                                      final EditTextListener positiveListener) {
        final EditText et = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins((int) PxDpUtil.convertPixelsToDp(20f, context), 0,
                (int) PxDpUtil.convertPixelsToDp(20f, context), 0);
        et.setLayoutParams(lp);

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(title)
                .setMessage(message)
                .setView(et)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positiveListener.positiveListener(dialog, which, et);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertBuilder.create().show();
    }

    public interface SimpleListener {
        public void positiveListener(DialogInterface dialog, int which);
    }

    public interface EditTextListener {
        public void positiveListener(DialogInterface dialog, int which, EditText editText);
    }
}
