package com.sticket.app.sticket.activities.sticker.sticon_editor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.util.ImageViewUtil;
import com.sticket.app.sticket.util.Landmark;
import com.sticket.app.sticket.util.ViewPagerAdapter;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerIconEvent;
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

    private ViewPagerAdapter adapter;

    private Map<Landmark, Sticker> stickerMap;
    private Map<Landmark, Button> buttonMap;
    private Landmark currentLandmark = Landmark.EYE_LEFT;

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticon_editor);

        ButterKnife.bind(this);

        stickerMap = new HashMap<>();
        buttonMap = new HashMap<>();

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
                for (Landmark key : stickerMap.keySet()) {
                    if (sticker.equals(stickerMap.get(key))) {
                        if (!stickerMap.containsKey(currentLandmark)) {
                            currentBtn.setBackground(getDrawable(R.drawable.btn_gray));
                        } else {
                            currentBtn.setBackground(getDrawable(R.drawable.btn_green));
                        }

                        currentBtn = buttonMap.get(key);
                        currentBtn.setBackground(getDrawable(R.drawable.btn_pink));
                        break;
                    }
                }
            }

            @Override
            public void onStickerDeleted(Sticker sticker) {
                for (Landmark key : stickerMap.keySet()) {
                    if (sticker.equals(stickerMap.get(key))) {
                        if(!currentBtn.equals(buttonMap.get(key))) {
                            buttonMap.get(key).setBackground(getDrawable(R.drawable.btn_gray));
                        }
                        stickerMap.remove(key);
                        break;
                    }
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

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
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
        };

        leftEyeBtn.setOnClickListener(onClickListener);
        rightEyeBtn.setOnClickListener(onClickListener);
        GlassesBtn.setOnClickListener(onClickListener);
        leftCheekBtn.setOnClickListener(onClickListener);
        rightCheekBtn.setOnClickListener(onClickListener);
        noseBtn.setOnClickListener(onClickListener);
        mouthBtn.setOnClickListener(onClickListener);

        buttonMap.put(Landmark.EYE_LEFT, leftEyeBtn);
        buttonMap.put(Landmark.EYE_RIGHT, rightEyeBtn);
        buttonMap.put(Landmark.GLASSES, GlassesBtn);
        buttonMap.put(Landmark.CHEEK_LEFT, leftCheekBtn);
        buttonMap.put(Landmark.CHEEK_RIGHT, rightCheekBtn);
        buttonMap.put(Landmark.NOSE, noseBtn);
        buttonMap.put(Landmark.MOUTH, mouthBtn);
    }

    private void addAsset(final Bitmap bitmap, float xPercent, float yPercent, Landmark landmark) {
        if (stickerMap.get(landmark) != null) {
            stickerView.remove(stickerMap.get(landmark));
        }

        Sticker sticker = new DrawableSticker(new BitmapDrawable(getResources(), bitmap));

        stickerMap.put(landmark, sticker);

        Matrix matrix = sticker.getMatrix();

        stickerView.addSticker(sticker);

        float xOffset = (stickerView.getWidth() - avartarImg.getDrawable().getIntrinsicWidth()) / 2f
                + ImageViewUtil.getAbstractXByPercent(avartarImg, xPercent);
        float yOffset = (stickerView.getHeight() - avartarImg.getDrawable().getIntrinsicHeight()) / 2f
                + ImageViewUtil.getAbstractYByPercent(avartarImg, yPercent);
        float xScaleOffset = (float) bitmap.getWidth() / (float) sticker.getWidth();
        float yScaleOffset = (float) bitmap.getHeight() / (float) sticker.getHeight();

        matrix.postScale(0.3f, 0.3f, xOffset, yOffset);

        sticker.setMatrix(matrix);

        stickerView.invalidate();
    }

    @OnClick(R.id.btn_sticon_editor_finish)
    public void btnFinished(View view) {
        finish();
    }

    @OnClick(R.id.btn_sticon_editor_back)
    public void btnGoBack(View view) {
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());     // getFragmentManager() -> getChildFragmentManager() in BottomSheetDialogFragment

        adapter.init(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        (int) parent.getItemAtPosition(position));
                addAsset(bitmap, currentLandmark.getX(), currentLandmark.getY(), currentLandmark);
            }
        });

        viewPager.setAdapter(adapter);
    }
}
