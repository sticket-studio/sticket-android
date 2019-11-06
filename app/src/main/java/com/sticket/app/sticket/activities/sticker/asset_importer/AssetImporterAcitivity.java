package com.sticket.app.sticket.activities.sticker.asset_importer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.util.Alert;
import com.sticket.app.sticket.util.FileUtil;
import com.sticket.app.sticket.util.Landmark;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AssetImporterAcitivity extends AppCompatActivity implements View.OnClickListener {

    final int PICTURE_REQUEST_CODE = 100;
    private static final String TAG = AssetImporterAcitivity.class.getSimpleName();

    ImageView goToGallery;
    ImageView selectedImg;
    BitmapStickerIcon sticker;
    RelativeLayout assetImpoerter;
    AssetImporterAdapter assetImporterAdapter;
    @BindView(R.id.btn_asset_editor_left_eye)
    Button leftEyeBtn;
    @BindView(R.id.btn_asset_editor_right_eye)
    Button rightEyeBtn;
    @BindView(R.id.btn_asset_editor_nose)
    Button noseBtn;
    @BindView(R.id.btn_asset_editor_left_cheek)
    Button leftCheekBtn;
    @BindView(R.id.btn_asset_editor_right_cheek)
    Button rightCheekBtn;
    @BindView(R.id.btn_asset_editor_mouth)
    Button mouthBtn;
    @BindView(R.id.sv_asset_editor_editor)
    StickerView stickerView;
    ImageView avartarImg;
    Bitmap bitmap;
    Button currentBtn;
    ImageButton finishBtn;
    private Landmark currentLandmark = Landmark.EYE_LEFT;
    private SticketDatabase database;


    private float dummyX;
    private float dummyY;

    ArrayList<Uri> galleryUriList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importer_asset);
        avartarImg = (ImageView) findViewById(R.id.img_asset_editor_avartar);
        assetImpoerter = (RelativeLayout) findViewById(R.id.layout_asset_editor_editor);
        finishBtn = (ImageButton) findViewById(R.id.btn_asset_editor_finish);
        RecyclerView recyclerView = findViewById(R.id.recyler_asset_impoter_gallery_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        galleryUriList = getVideo();
        assetImporterAdapter = new AssetImporterAdapter(galleryUriList);
        recyclerView.setAdapter(assetImporterAdapter);
        //selectedImg = (ImageView) findViewById(R.id.iv_asset_importer_selectedImg) ;
        goToGallery = (ImageView) findViewById(R.id.iv_asset_importer_gotoGallery);
        goToGallery.setImageResource(R.drawable.img_gallery);
        goToGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGallery = new Intent();
                goToGallery.setType("image/*");
                goToGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(goToGallery, 1);
            }
        });

        assetImporterAdapter.setItemClick(new AssetImporterAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                try {
                    Log.e(TAG, Uri.parse("file://" + galleryUriList.get(position)).toString() + " : check");
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse("file://" + galleryUriList.get(position))));
                    Sticker sticker = new DrawableSticker(new BitmapDrawable(getResources(), bitmap));
                    Log.e("check", sticker.toString());
                    stickerView.addSticker(sticker);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        initViews();
        ButterKnife.bind(this);
        database = SticketDatabase.getDatabase(this);
        ButterKnife.bind(this);


        currentBtn = leftEyeBtn;
    }

    private void initViews() {
        currentBtn = leftEyeBtn;
        dummyX = avartarImg.getDrawable().getIntrinsicWidth();
        dummyY = avartarImg.getDrawable().getIntrinsicHeight();
    }

    @OnClick({R.id.btn_asset_editor_back})
    public void goBack(View v) {
        finish();
    }

    @OnClick({R.id.btn_asset_editor_finish})
    public void finishClick(View v) {
        if (currentLandmark == null || stickerView.getCurrentSticker()==null) {
            Alert.makeText("가져올 에셋을 선택해주세요");
            return;
        }

        Asset asset = new Asset();
        asset.setImgUrl(null);
        Landmark landmark = currentLandmark;

        float xAssetOffset = (stickerView.getWidth() - dummyX) / 2f + dummyX * landmark.getX() / 100f
                - stickerView.getCurrentSticker().getWidth() / 2f;
        float yAssetOffset = (stickerView.getHeight() - dummyY) / 2f + dummyY * landmark.getY() / 100f
                - stickerView.getCurrentSticker().getHeight() / 2f;

        int isFlipped = stickerView.getCurrentSticker().isFlippedHorizontally() ? 1 : 0;
        int rotate = (int) stickerView.getCurrentSticker().getCurrentAngle();
        double ratio = stickerView.getCurrentSticker().getCurrentScale();


        float offsetX = (float) (stickerView.getCurrentSticker().getMappedCenterPoint().x - xAssetOffset) / bitmap.getWidth();
        // offsetY는 반대 (-)
        float offsetY = -(float) (stickerView.getCurrentSticker().getMappedCenterPoint().y - yAssetOffset) / bitmap.getHeight();


        String fileName = "thumbnail" + bitmap;
        FileUtil.structDirectories();
        FileUtil.saveBitmapToFile(bitmap, FileUtil.THUMBNAIL_ASSET_DIRECTORY_PATH, fileName);
        asset.setLocalUrl(FileUtil.THUMBNAIL_ASSET_DIRECTORY_PATH + "/" + fileName + ".png");

        asset.setOffset_x(offsetX);
        asset.setOffset_y(offsetY);
        asset.setFlip(isFlipped);
        asset.setRotate(rotate);
        asset.setLandmark(landmark);
        asset.setRatio(ratio);
        asset.setType(Asset.TYPE_OWN);
        database.assetDao().insert(asset);

        finish();

    }


    @OnClick({R.id.btn_asset_editor_left_eye, R.id.btn_asset_editor_right_eye,
            R.id.btn_asset_editor_nose, R.id.btn_asset_editor_left_cheek,
            R.id.btn_asset_editor_right_cheek, R.id.btn_asset_editor_mouth})
    public void onClick(View v) {

        currentBtn = (Button) v;
        currentBtn.setBackground(getDrawable(R.drawable.btn_pink));

        switch (v.getId()) {
            case R.id.btn_asset_editor_left_eye:
                currentLandmark = Landmark.EYE_LEFT;
                rightEyeBtn.setBackground(getDrawable(R.drawable.btn_gray));
                noseBtn.setBackground(getDrawable(R.drawable.btn_gray));
                leftCheekBtn.setBackground(getDrawable(R.drawable.btn_gray));
                rightCheekBtn.setBackground(getDrawable(R.drawable.btn_gray));
                mouthBtn.setBackground(getDrawable(R.drawable.btn_gray));
                break;
            case R.id.btn_asset_editor_right_eye:
                leftEyeBtn.setBackground(getDrawable(R.drawable.btn_gray));
                noseBtn.setBackground(getDrawable(R.drawable.btn_gray));
                leftCheekBtn.setBackground(getDrawable(R.drawable.btn_gray));
                rightCheekBtn.setBackground(getDrawable(R.drawable.btn_gray));
                mouthBtn.setBackground(getDrawable(R.drawable.btn_gray));
                currentLandmark = Landmark.EYE_RIGHT;
                break;

            case R.id.btn_asset_editor_left_cheek:
                currentLandmark = Landmark.CHEEK_LEFT;
                leftEyeBtn.setBackground(getDrawable(R.drawable.btn_gray));
                rightEyeBtn.setBackground(getDrawable(R.drawable.btn_gray));
                noseBtn.setBackground(getDrawable(R.drawable.btn_gray));
                rightCheekBtn.setBackground(getDrawable(R.drawable.btn_gray));
                mouthBtn.setBackground(getDrawable(R.drawable.btn_gray));
                break;
            case R.id.btn_asset_editor_right_cheek:
                rightEyeBtn.setBackground(getDrawable(R.drawable.btn_gray));
                noseBtn.setBackground(getDrawable(R.drawable.btn_gray));
                leftCheekBtn.setBackground(getDrawable(R.drawable.btn_gray));
                mouthBtn.setBackground(getDrawable(R.drawable.btn_gray));
                currentLandmark = Landmark.CHEEK_RIGHT;
                break;
            case R.id.btn_asset_editor_nose:
                leftEyeBtn.setBackground(getDrawable(R.drawable.btn_gray));
                rightEyeBtn.setBackground(getDrawable(R.drawable.btn_gray));
                rightCheekBtn.setBackground(getDrawable(R.drawable.btn_gray));
                leftCheekBtn.setBackground(getDrawable(R.drawable.btn_gray));
                mouthBtn.setBackground(getDrawable(R.drawable.btn_gray));
                currentLandmark = Landmark.NOSE;
                break;
            case R.id.btn_asset_editor_mouth:
                rightEyeBtn.setBackground(getDrawable(R.drawable.btn_gray));
                rightCheekBtn.setBackground(getDrawable(R.drawable.btn_gray));
                leftCheekBtn.setBackground(getDrawable(R.drawable.btn_gray));
                noseBtn.setBackground(getDrawable(R.drawable.btn_gray));
                currentLandmark = Landmark.MOUTH_BOTTOM;
                break;
        }
    }

    private ArrayList<Uri> getVideo() {
        String[] proj = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA
        };
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, proj, null, null, MediaStore.Images.Media._ID + " COLLATE LOCALIZED DESC");
        ArrayList<Uri> gelleryUriList = new ArrayList<>();
        assert cursor != null;
        int count = 0;
        while (count < 10) {
            cursor.moveToNext();
            Log.i("uri check", Uri.parse(cursor.getString(1)).toString());

            Log.i("uri check", Uri.parse(cursor.getString(2)).toString());
            gelleryUriList.add(Uri.parse(cursor.getString(2)));
            count++;
        }
        cursor.close();
        return gelleryUriList;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    bitmap = img;
                    Sticker sticker = new DrawableSticker(new BitmapDrawable(getResources(), bitmap));
                    stickerView.addSticker(sticker);
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
