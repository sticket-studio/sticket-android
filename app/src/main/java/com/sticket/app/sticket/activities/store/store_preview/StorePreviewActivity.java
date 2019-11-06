package com.sticket.app.sticket.activities.store.store_preview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.models.Asset;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.CustomCallback;
import com.sticket.app.sticket.util.AlertDialogBuilderUtil;
import com.sticket.app.sticket.util.Landmark;
import com.sticket.app.sticket.util.SimpleCallbackUtil;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;

import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class StorePreviewActivity extends AppCompatActivity {
    private static final String TAG = StorePreviewActivity.class.getSimpleName();

    @BindView(R.id.btn_store_preview_back)
    ImageButton btnBack;
    @BindView(R.id.btn_store_preview_finish)
    ImageButton btnFinish;
    @BindView(R.id.btn_store_preview_buy)
    Button btnBuy;
    @BindView(R.id.img_store_preview_avartar)
    ImageView avartarImg;
    @BindView(R.id.tv_store_preview_asset_name)
    TextView assetNameTxt;
    @BindView(R.id.tv_store_preview_author_name)
    TextView authorName;
    @BindView(R.id.tv_store_preview_asset_stick)
    TextView assetSitck;
    @BindView(R.id.tv_store_preview_asset_like_num)
    TextView assetLikeNum;
    String lanmarkStr;
    Asset targetAsset;
    Landmark landmark;

    @BindView(R.id.layout_store_preview)
    RelativeLayout editorLayout;
    @BindView(R.id.sv_store_preview_editor)
    StickerView stickerView;

    float dummyX;
    float dummyY;
    float getX;
    float getY;


    @OnClick(R.id.btn_store_preview_finish)
    public void activityFinish() {
        finish();
    }

    @OnClick(R.id.btn_store_preview_back)
    public void activityBack() {
        finish();
    }

    @OnClick(R.id.btn_store_preview_buy)
    public void buyAsset() {
        AlertDialogBuilderUtil.simpleDialog(StorePreviewActivity.this, "에셋 구매",
                assetName + "(을)를 구매하시겠습니까?",
                (dialog, which) -> {
                    ApiClient.getInstance().getAssetService()
                            .PurchaseAsset(assetId)
                            .enqueue(SimpleCallbackUtil.getSimpleCallback());
                });
    }

    private int assetId;
    private String assetName;

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_preview);

        ButterKnife.bind(this);
        initViews();

        Intent intent = getIntent();
        if (intent == null) {
            Log.i("test", "안받아짐");
        }
        assetName = intent.getStringExtra("assetName");
        Log.i(TAG, "intentAssetName: " + assetName);
        assetId = Integer.parseInt(intent.getStringExtra("assetId"));
        Log.i(TAG, "intentAssetId: " + assetId);

        ApiClient.getInstance().getAssetService()
                .getAssetById(assetId)
                .enqueue(new CustomCallback<Asset>() {
                    @Override
                    public void onResponse(Call<Asset> call, Response<Asset> response) {
                        if (response.body() == null) {
                            //없을때 띄우기
                        }
                        targetAsset = response.body();
                        assetNameTxt.setText(targetAsset.getName());
                        assetLikeNum.setText(targetAsset.getLikeCnt() + "");
                        assetSitck.setText(targetAsset.getPrice() + "");
                        Log.i("price", targetAsset.getPrice() + "");
                        authorName.setText(targetAsset.getAuthor().getName());
                        lanmarkStr = targetAsset.getLandmark();
                        Log.i("landmark str", lanmarkStr);


                        if (lanmarkStr.equals("EYE_LEFT")) {
                            landmark = Landmark.EYE_LEFT;
                        } else if (lanmarkStr.equals("MOUTH_BOTTOM")) {
                            landmark = Landmark.MOUTH_BOTTOM;
                        } else if (lanmarkStr.equals("NOSE")) {
                            landmark = Landmark.NOSE;
                        } else if (lanmarkStr.equals("EAR_LEFT")) {
                            landmark = Landmark.EAR_LEFT;
                        } else if (lanmarkStr.equals("CHEEK_LEFT")) {
                            landmark = Landmark.CHEEK_LEFT;
                        }
                        Log.i("landmark", landmark.getKorName());

                        getX = 2 * landmark.getX() - 100;
                        getY = 2 * landmark.getY() - 100;
                        onPost(targetAsset.getImgUrl());
                        Log.i("xy landmark", avartarImg.getWidth() + " " + avartarImg.getHeight());
                    }
                });

    }

    private void initViews() {
        dummyX = avartarImg.getDrawable().getIntrinsicWidth();
        dummyY = avartarImg.getDrawable().getIntrinsicHeight();
    }

    public void onPost(String url) {
        new Thread(new Runnable() {
            public void run() {
                try {

                    final Bitmap b = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());

                    Sticker sticker = new DrawableSticker(new BitmapDrawable(getResources(), b));
                    Log.i("stickerInfo", sticker.getHeight() + "");
                    Matrix mt = sticker.getMatrix();

                    getX = 2 * landmark.getX() - 100 + (sticker.getWidth() / dummyX) * 100;
                    getY = 2 * landmark.getY() - 100;
                    mt.setTranslate(getX, getY);
                    Log.i("test", (sticker.getWidth() / dummyX) * 100 + " ");
                    Log.i("xy", getX + " " + getY);
                    sticker.setMatrix(mt);
                    stickerView.addSticker(sticker);
                    stickerView.setLocked(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
