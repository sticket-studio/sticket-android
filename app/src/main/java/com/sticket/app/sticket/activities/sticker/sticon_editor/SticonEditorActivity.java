package com.sticket.app.sticket.activities.sticker.sticon_editor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;
import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.sticker.StickerGridFragment;
import com.sticket.app.sticket.util.MyBitmapFactory;
import com.sticket.app.sticket.util.ViewPagerAdapter;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SticonEditorActivity extends AppCompatActivity {

    @BindView(R.id.layout_sticon_editor_editor)
    RelativeLayout editorLayout;
    @BindView(R.id.viewpager_sticon_editor)
    ViewPager assetViewPager;
    @BindView(R.id.tab_sticon_editor)
    TabLayout assetTabLayout;
    @BindView(R.id.svEditor)
    StickerView stickerView;


    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticon_editor);

        ButterKnife.bind(this);

        initViews();

        Bitmap bitmap = MyBitmapFactory.getInstance().getBitmap(FirebaseVisionFaceLandmark.LEFT_EYE);
        addAsset(bitmap);
    }

    private void initViews(){
        setupViewPager(assetViewPager);

        assetTabLayout.setupWithViewPager(assetViewPager);
    }

    private void addAsset(final Bitmap bitmap){

        Sticker sticker = new DrawableSticker(new BitmapDrawable(getResources(), bitmap));
        Sticker sticker2 = new DrawableSticker(new BitmapDrawable(getResources(), bitmap));

        stickerView.addSticker(sticker);
        stickerView.addSticker(sticker2);
    }

    public void btnFinished(View view){

    }

    @OnClick(R.id.btn_sticon_editor_back)
    public void btnGoBack(View view) {
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());     // getFragmentManager() -> getChildFragmentManager() in BottomSheetDialogFragment
        adapter.addFrag(new StickerGridFragment(), "눈");
        adapter.addFrag(new StickerGridFragment(), "코");
        adapter.addFrag(new StickerGridFragment(), "입");
        adapter.addFrag(new StickerGridFragment(), "볼");
        adapter.addFrag(new StickerGridFragment(), "귀걸이");
        viewPager.setAdapter(adapter);
    }
}
