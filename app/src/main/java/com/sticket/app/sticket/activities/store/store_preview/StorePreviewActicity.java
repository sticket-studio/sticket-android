package com.sticket.app.sticket.activities.store.store_preview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.models.Asset;
import com.sticket.app.sticket.models.Stick;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.retrofit.client.CustomCallback;
import com.sticket.app.sticket.retrofit.dto.response.asset.SimpleAssetResponse;
import com.sticket.app.sticket.util.Landmark;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class StorePreviewActicity extends AppCompatActivity {

    @BindView(R.id.btn_store_preview_back)
    ImageButton btnBack;
    @BindView(R.id.btn_store_preview_finish)
    ImageButton btnFinish;
    @BindView(R.id.img_store_preview_avartar)
    ImageView avartarImg;
    @BindView(R.id.tv_store_preview_asset_name)
    TextView assetName;
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
    public void acitivityFinish(){
        finish();
    }
    @OnClick(R.id.btn_store_preview_back)
    public void activityBack(){
        finish();
    }



    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_preview);

        ButterKnife.bind(this);
        initViews();

        Intent intent = getIntent();
        if(intent == null){
            Log.i("test","안받아짐");
        }
        String intentAssetName = intent.getStringExtra("assetName");
        Log.i("intentAssetName",intentAssetName);
        String intentAssetId = intent.getStringExtra("assetId");
        Log.i("intentAssetId",intentAssetId);

        ApiClient.getInstance().getAssetService()
                .getAssetById(Integer.parseInt(intentAssetId))
                .enqueue(new CustomCallback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                if(response.body()==null){
                    //없을때 띄우기
                }
                targetAsset = response.body();
                assetName.setText(targetAsset.getName());
                assetLikeNum.setText(targetAsset.getLikeCnt()+"");
                assetSitck.setText(targetAsset.getPrice()+"");
                Log.i("price",targetAsset.getPrice()+"");
                authorName.setText(targetAsset.getAuthor().getName());
                lanmarkStr = targetAsset.getLandmark();
                Log.i("landmark str",lanmarkStr);


                if(lanmarkStr.equals("EYE_LEFT")){
                    landmark = Landmark.EYE_LEFT;
                }else if(lanmarkStr.equals("MOUTH_BOTTOM")) {
                    landmark =Landmark.MOUTH;
                }else if(lanmarkStr.equals("NOSE")) {
                    landmark = Landmark.NOSE;
                }else if(lanmarkStr.equals("EAR_LEFT")){
                    landmark = Landmark.EAR_LEFT;
                }else if(lanmarkStr.equals("CHEEK_LEFT")){
                    landmark = Landmark.CHEEK_LEFT;
                }
                Log.i("landmark",landmark.getKorName());

                getX = 2*landmark.getX()-100;
                getY = 2*landmark.getY()-100;
                onPost(targetAsset.getImgUrl());
                Log.i("xy landmark",avartarImg.getWidth()+" "+avartarImg.getHeight());

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
                    Log.i("stickerInfo",sticker.getHeight()+"");
                    Matrix mt = sticker.getMatrix();

                    getX =2*landmark.getX()-100 +(sticker.getWidth()/dummyX)*100;
                    getY =2*landmark.getY()-100 ;
                    mt.setTranslate(getX,getY);
                    Log.i("test",(sticker.getWidth()/dummyX)*100+" ");
                    Log.i("xy",getX+" "+getY);
                    sticker.setMatrix(mt);
                    stickerView.addSticker(sticker);
                    stickerView.setLocked(true);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }





}
