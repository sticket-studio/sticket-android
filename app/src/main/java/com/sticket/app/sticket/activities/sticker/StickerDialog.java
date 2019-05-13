package com.sticket.app.sticket.activities.sticker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.sticker.sticon_editor.SticonEditorActivity;
import com.sticket.app.sticket.util.ViewPagerAdapter;


public class StickerDialog extends BottomSheetDialogFragment {

    private Button btnCapture;      // TODO : Search Custom Listener
    private Button assetImporterBtn, sticonEditorBtn, motionticonEditorBtn;
    private View view;

    public StickerDialog() {
        super();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.BaseDialogTheme);
    }

    @android.support.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_sticker, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        ViewPager stickerDialogViewPager = (ViewPager) view.findViewById(R.id.sticker_dialog_viewpager);

        setupViewPager(stickerDialogViewPager);

        TabLayout stickerDialogTabLayout = (TabLayout) view.findViewById(R.id.sticker_dialog_tab);
        stickerDialogTabLayout.setupWithViewPager(stickerDialogViewPager);

        // OnClickListener for dismiss
        LinearLayout layoutBtnEditor = (LinearLayout) view.findViewById(R.id.layoutStickerEditor);
        layoutBtnEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        assetImporterBtn = view.findViewById(R.id.btnAssetImporter);
        sticonEditorBtn = view.findViewById(R.id.btnSticonEditor);
        motionticonEditorBtn = view.findViewById(R.id.btnMotionEditor);

        initListener();
    }

    private void initListener() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnAssetImporter:

                        break;
                    case R.id.btnSticonEditor:
                        Intent sticonEditorIntent = new Intent(getContext(), SticonEditorActivity.class);
                        startActivity(sticonEditorIntent);
                        break;
                    case R.id.btnMotionEditor:

                        break;
                }
            }
        };

        assetImporterBtn.setOnClickListener(onClickListener);
        sticonEditorBtn.setOnClickListener(onClickListener);
        motionticonEditorBtn.setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());     // getFragmentManager() -> getChildFragmentManager() in BottomSheetDialogFragment
        adapter.addFrag(new StickerGridFragment(), "눈");
        adapter.addFrag(new StickerGridFragment(), "코");
        adapter.addFrag(new StickerGridFragment(), "입");
        adapter.addFrag(new StickerGridFragment(), "볼");
        adapter.addFrag(new StickerGridFragment(), "귀걸이");
        adapter.addFrag(new StickerGridFragment(), "스티커");
        adapter.addFrag(new StickerGridFragment(), "모션티콘");
        viewPager.setAdapter(adapter);
    }

}