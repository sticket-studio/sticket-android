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
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.sticker.asset_importer.AssetImporter;
import com.sticket.app.sticket.activities.sticker.sticon_editor.SticonEditorActivity;
import com.sticket.app.sticket.database.DBTest;
import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.Motionticon;
import com.sticket.app.sticket.database.entity.Sticon;
import com.sticket.app.sticket.database.entity.SticonAsset;
import com.sticket.app.sticket.util.ViewPagerAdapter;

import java.util.List;


public class StickerDialog extends BottomSheetDialogFragment {
    private static final String TAG = StickerDialog.class.getSimpleName();

    private Button btnCapture;      // TODO : Search Custom Listener
    private ImageButton assetImporterBtn, sticonEditorBtn;
    private Button motionticonEditorBtn;
    private View view;

    private List<Asset> assetList;
    private List<SticonAsset> sticonAssetList;
    private List<Sticon> sticonList;
    private List<Motionticon> motionticonList;

    public StickerDialog() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.BaseDialogTheme);

        SticketDatabase database = SticketDatabase.getDatabase(getContext());
        assetList = database.assetDao().getAllassets();
        sticonAssetList = database.sticonAssetDao().getAllSticon_assets();
        sticonList = database.sticonDao().getAllSticon();
        motionticonList = database.motionticonDao().getAllMotionticons();

        DBTest.printInfo(getContext());
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
        LinearLayout layoutBtnEditor = (LinearLayout) view.findViewById(R.id.layout_sticker_editor);
        layoutBtnEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        assetImporterBtn = view.findViewById(R.id.btn_asset_importer);
        sticonEditorBtn = view.findViewById(R.id.btn_sticon_editor);
        motionticonEditorBtn = view.findViewById(R.id.btn_motion_editor);

        initListener();
    }

    private void initListener() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_asset_importer:
                        Intent assetImpoterIntent = new Intent(getContext(), AssetImporter.class);
                        startActivity(assetImpoterIntent);
                        break;
                    case R.id.btn_sticon_editor:
                        Intent sticonEditorIntent = new Intent(getContext(), SticonEditorActivity.class);
                        startActivity(sticonEditorIntent);
                        break;
                    case R.id.btn_motion_editor:

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
