package com.sticket.app.sticket.util;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sticket.app.sticket.R;

import static com.sticket.app.sticket.util.Alert.context;


//public class StickerDialog extends Activity {
//
//    private Context context;
//
//    public StickerDialog(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_sticker);
//
//    }
//
//    public void openDialog() {
//        final Dialog dlg = new Dialog(context);
//        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dlg.setContentView(R.layout.dialog_sticker);
//        dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        dlg.setCanceledOnTouchOutside(true);
//
//        Window window = dlg.getWindow();
//        WindowManager.LayoutParams params = window.getAttributes();
//        window.setGravity(Gravity.BOTTOM);
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        window.setAttributes(params);
//        dlg.show();
//    }
//
//
//}

public class StickerDialog extends BottomSheetDialogFragment {

    @android.support.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_sticker, container, false);

        return v;
    }

}
