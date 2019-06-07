package com.sticket.app.sticket.activities.sticker.sticon_editor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.Sticon;
import com.sticket.app.sticket.database.entity.SticonAsset;
import com.sticket.app.sticket.util.FileUtil;
import com.sticket.app.sticket.util.ImageViewUtil;
import com.sticket.app.sticket.util.Landmark;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SticonEditorActivity extends AppCompatActivity {
    private static final String TAG = SticonEditorActivity.class.getSimpleName();

    @BindView(R.id.layout_sticon_editor_editor)
    RelativeLayout editorLayout;
    @BindView(R.id.viewpager_sticon_editor)
    ViewPager assetViewPager;
    @BindView(R.id.img_sticon_editor_avartar)
    ImageView avartarImg;
    @BindView(R.id.tab_sticon_editor)
    TabLayout assetTabLayout;
    @BindView(R.id.sv_sticon_editor_editor)
    StickerView stickerView;
    @BindView(R.id.btn_sticon_editor_left_eye)
    Button leftEyeBtn;
    @BindView(R.id.btn_sticon_editor_right_eye)
    Button rightEyeBtn;
    @BindView(R.id.btn_sticon_editor_glasses)
    Button GlassesBtn;
    @BindView(R.id.btn_sticon_editor_nose)
    Button noseBtn;
    @BindView(R.id.btn_sticon_editor_left_cheek)
    Button leftCheekBtn;
    @BindView(R.id.btn_sticon_editor_right_cheek)
    Button rightCheekBtn;
    @BindView(R.id.btn_sticon_editor_mouth)
    Button mouthBtn;

    Button currentBtn;

    private SticonEditorViewPagerAdapter adapter;

    private Map<Landmark, Sticker> stickerMap;
    private Map<Sticker, Landmark> landmarkMap;
    private Map<Landmark, Button> buttonMap;
    private Map<Landmark, Asset> assetMap;
    private Map<Landmark, SticonAsset> sticonAssetMap;
    private Map<Landmark, Bitmap> bitmapMap;
    private Landmark currentLandmark = Landmark.EYE_LEFT;
    private SticketDatabase database;

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticon_editor);

        ButterKnife.bind(this);

        database = SticketDatabase.getDatabase(this);

        landmarkMap = new HashMap<>();
        stickerMap = new HashMap<>();
        buttonMap = new HashMap<>();
        assetMap = new HashMap<>();
        sticonAssetMap = new HashMap<>();
        bitmapMap = new HashMap<>();

        initViews();
        initListener();
    }

    private void initViews() {
        setupViewPager(assetViewPager);

        assetTabLayout.setupWithViewPager(assetViewPager);

        currentBtn = leftEyeBtn;
    }

    private void initListener() {
        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerClicked(Sticker sticker) {
                Landmark landmark = landmarkMap.get(sticker);

                if (sticker.equals(stickerMap.get(landmark))) {
                    if (!stickerMap.containsKey(currentLandmark)) {
                        currentBtn.setBackground(getDrawable(R.drawable.btn_gray));
                    } else {
                        currentBtn.setBackground(getDrawable(R.drawable.btn_green));
                    }

                    currentBtn = buttonMap.get(landmark);
                    currentBtn.setBackground(getDrawable(R.drawable.btn_pink));
                }
            }

            @Override
            public void onStickerDeleted(Sticker sticker) {
                Landmark landmark = landmarkMap.get(sticker);
                if (sticker.equals(stickerMap.get(landmark))) {
                    if (!currentBtn.equals(buttonMap.get(landmark))) {
                        buttonMap.get(landmark).setBackground(getDrawable(R.drawable.btn_gray));
                    }

                    Log.e(TAG, "onStickerDeleted");
                    stickerMap.remove(landmark);
                    sticonAssetMap.remove(landmark);
                    assetMap.remove(landmark);
                    landmarkMap.remove(sticker);
                }
            }

            @Override
            public void onStickerDragFinished(Sticker sticker) {
            }

            @Override
            public void onStickerZoomFinished(Sticker sticker) {
            }

            @Override
            public void onStickerFlipped(Sticker sticker) {
            }

            @Override
            public void onStickerDoubleTapped(Sticker sticker) {
            }
        });

        buttonMap.put(Landmark.EYE_LEFT, leftEyeBtn);
        buttonMap.put(Landmark.EYE_RIGHT, rightEyeBtn);
        buttonMap.put(Landmark.GLASSES, GlassesBtn);
        buttonMap.put(Landmark.CHEEK_LEFT, leftCheekBtn);
        buttonMap.put(Landmark.CHEEK_RIGHT, rightCheekBtn);
        buttonMap.put(Landmark.NOSE, noseBtn);
        buttonMap.put(Landmark.MOUTH, mouthBtn);
    }

    @OnClick({R.id.btn_sticon_editor_left_eye, R.id.btn_sticon_editor_right_eye,
            R.id.btn_sticon_editor_glasses, R.id.btn_sticon_editor_nose,
            R.id.btn_sticon_editor_left_cheek, R.id.btn_sticon_editor_right_cheek
            , R.id.btn_sticon_editor_mouth})
    public void onClick(View v) {
        if (!stickerMap.containsKey(currentLandmark)) {
            currentBtn.setBackground(getDrawable(R.drawable.btn_gray));
        } else {
            currentBtn.setBackground(getDrawable(R.drawable.btn_green));
        }

        currentBtn = (Button) v;
        currentBtn.setBackground(getDrawable(R.drawable.btn_pink));

        switch (v.getId()) {
            case R.id.btn_sticon_editor_left_eye:
                currentLandmark = Landmark.EYE_LEFT;
                break;
            case R.id.btn_sticon_editor_right_eye:
                currentLandmark = Landmark.EYE_RIGHT;
                break;
            case R.id.btn_sticon_editor_glasses:
                currentLandmark = Landmark.GLASSES;
                break;
            case R.id.btn_sticon_editor_left_cheek:
                currentLandmark = Landmark.CHEEK_LEFT;
                break;
            case R.id.btn_sticon_editor_right_cheek:
                currentLandmark = Landmark.CHEEK_RIGHT;
                break;
            case R.id.btn_sticon_editor_nose:
                currentLandmark = Landmark.NOSE;
                break;
            case R.id.btn_sticon_editor_mouth:
                currentLandmark = Landmark.MOUTH;
                break;
        }
    }

    private void postAsset(final Asset asset, Landmark landmark) {
        if (stickerMap.get(landmark) != null) {
            stickerView.remove(stickerMap.get(landmark));
        }

        Bitmap bitmap = BitmapFactory.decodeFile(asset.getLocal_url());

        Sticker sticker = new DrawableSticker(new BitmapDrawable(getResources(), bitmap));
        stickerMap.put(landmark, sticker);
        assetMap.put(landmark, asset);
        landmarkMap.put(sticker, landmark);
        bitmapMap.put(landmark,bitmap);
        stickerView.addSticker(sticker);

        float xPercent = landmark.getX();
        float yPercent = landmark.getY();

        float xOffset = (stickerView.getWidth() - avartarImg.getDrawable().getIntrinsicWidth()) / 2f
                + ImageViewUtil.getAbstractXByPercent(avartarImg, xPercent);
        float yOffset = (stickerView.getHeight() - avartarImg.getDrawable().getIntrinsicHeight()) / 2f
                + ImageViewUtil.getAbstractYByPercent(avartarImg, yPercent);
        float xScaleOffset = (float) bitmap.getWidth() / (float) sticker.getWidth();
        float yScaleOffset = (float) bitmap.getHeight() / (float) sticker.getHeight();
        sticker.getMatrix().postScale(0.3f, 0.3f, xOffset, yOffset);

        stickerView.invalidate();

        SticonAsset sticonAsset = new SticonAsset();
        sticonAsset.setAssetIdx(asset.getIdx());
        sticonAsset.setOffsetX(sticker.getMappedCenterPoint().x);
        sticonAsset.setOffsetY(sticker.getMappedCenterPoint().y);
        sticonAssetMap.put(landmark, sticonAsset);
    }

    @OnClick(R.id.btn_sticon_editor_finish)
    public void btnFinished(View view) {
        Sticon sticon = new Sticon();
        sticon.setImg_url("");
        Bitmap thumbnail = stickerView.createBitmap();

        long newSticonId = database.sticonDao().insert(sticon);

        String fileName = "thumbnail" + newSticonId;
        FileUtil.saveBitmapToFile(thumbnail, FileUtil.THUMBNAIL_STICON_DIRECTORY_PATH, fileName);

        sticon.setIdx((int)newSticonId);
        sticon.setLocal_url(FileUtil.THUMBNAIL_STICON_DIRECTORY_PATH + "/" + fileName + ".png");
        database.sticonDao().update(sticon);

        for (Sticker sticker : landmarkMap.keySet()) {
            Landmark landmark = landmarkMap.get(sticker);
            SticonAsset sticonAsset = sticonAssetMap.get(landmark);
            Bitmap bitmap = bitmapMap.get(landmark);

            int isFlipped = sticker.isFlippedHorizontally() ? 1 : 0;
            int rotate = (int) sticker.getCurrentAngle();

            sticonAsset.setSticonIdx((int) newSticonId);
            float offsetX = (float)(sticker.getMappedCenterPoint().x - sticonAsset.getOffsetX())/ bitmap.getWidth();
            // offsetY는 반대 (-)
            float offsetY = -(float)(sticker.getMappedCenterPoint().y - sticonAsset.getOffsetY())/ bitmap.getHeight();

            Log.e(TAG, "xOffset : " + (float)(sticker.getMappedCenterPoint().x - sticonAsset.getOffsetX())/ bitmap.getWidth());

            sticonAsset.setOffsetX(offsetX);
            sticonAsset.setOffsetY(offsetY);
            sticonAsset.setFlip(isFlipped);
            sticonAsset.setRotate(rotate);
            sticonAsset.setLandmark(landmark);
            database.sticonAssetDao().insert(sticonAsset);
        }

        finish();
    }

    @OnClick(R.id.btn_sticon_editor_back)
    public void btnGoBack(View view) {
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new SticonEditorViewPagerAdapter(getSupportFragmentManager());     // getFragmentManager() -> getChildFragmentManager() in BottomSheetDialogFragment

        adapter.init(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Asset asset = (Asset) parent.getItemAtPosition(position);
                postAsset(asset, currentLandmark);
            }
        });

        viewPager.setAdapter(adapter);
    }
}
