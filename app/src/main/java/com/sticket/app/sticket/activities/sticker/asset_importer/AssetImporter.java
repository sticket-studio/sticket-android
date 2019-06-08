package com.sticket.app.sticket.activities.sticker.asset_importer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.sticker.sticon_editor.SticonEditorActivity;
import com.sticket.app.sticket.database.SticketDatabase;
import com.sticket.app.sticket.database.entity.Asset;
import com.sticket.app.sticket.database.entity.SticonAsset;
import com.sticket.app.sticket.util.ImageViewUtil;
import com.sticket.app.sticket.util.Landmark;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;



public class AssetImporter   extends AppCompatActivity implements View.OnClickListener {

    final  int PICTURE_REQUEST_CODE = 100;
    private static final String TAG = AssetImporter.class.getSimpleName();
    ImageView goToGallery;
    ImageView selectedImg;
    BitmapStickerIcon sticker;
    RelativeLayout assetImpoerter;
    AssetImporterAdapter assetImporterAdapter;
    @BindView(R.id.btn_asset_editor_left_eye)
    Button leftEyeBtn;
    @BindView(R.id.btn_asset_editor_right_eye)
    Button rightEyeBtn;
    @BindView(R.id.btn_asset_editor_glasses)
    Button GlassesBtn;
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
    Bitmap bitmap;
    Button currentBtn;


    private SticketDatabase database;


    ArrayList<Uri> galleryUriList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importer_asset);
        assetImpoerter = (RelativeLayout) findViewById(R.id.layout_asset_editor_editor);

        RecyclerView recyclerView = findViewById(R.id.recyler_asset_impoter_gallery_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        galleryUriList = getVideo();
        assetImporterAdapter = new AssetImporterAdapter(galleryUriList);
        recyclerView.setAdapter(assetImporterAdapter);
        selectedImg = (ImageView) findViewById(R.id.iv_asset_importer_selectedImg) ;
        goToGallery = (ImageView) findViewById(R.id.iv_asset_importer_gotoGallery);
        goToGallery.setImageResource(R.drawable.img_gallery);
        goToGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGallery = new Intent();
                goToGallery.setType("image/*");
                goToGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(goToGallery,1);
            }
        });

        assetImporterAdapter.setItemClick(new AssetImporterAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                try {

                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse("file://"+galleryUriList.get(position))));
                    Sticker sticker = new DrawableSticker(new BitmapDrawable(getResources(), bitmap));
                    stickerView.addSticker(sticker);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        });



        ButterKnife.bind(this);

        database = SticketDatabase.getDatabase(this);

        currentBtn = leftEyeBtn;
    }

    private ArrayList<Uri> getVideo() {
        String[] proj = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA
        };
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, proj, null, null, MediaStore.Images.Media._ID+" COLLATE LOCALIZED DESC");
        ArrayList<Uri> gelleryUriList = new ArrayList<>();
        assert cursor != null;
        int count=0;
        while(count<10){
            cursor.moveToNext();
            Log.i("uri check",Uri.parse(cursor.getString(1)).toString());

            Log.i("uri check",Uri.parse(cursor.getString(2)).toString());
            gelleryUriList.add(Uri.parse(cursor.getString(2)));
            count++;
        }
        cursor.close();
        return gelleryUriList;
    }


    public void onClick(View v) {


    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode ==1){
            if(resultCode==RESULT_OK){
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    bitmap = img;
                    Sticker sticker = new DrawableSticker(new BitmapDrawable(getResources(), bitmap));
                    stickerView.addSticker(sticker);
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }


}
