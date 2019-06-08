package com.sticket.app.sticket.activities.sticker.asset_importer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.sticket.app.sticket.R;
import java.io.InputStream;
import java.util.ArrayList;



public class AssetImporter   extends AppCompatActivity implements View.OnClickListener {

    final  int PICTURE_REQUEST_CODE = 100;
    ImageView goToGallery;
    ImageView selectedImg;
    FrameLayout assetImpoerter;
    AssetImporterAdapter assetImporterAdapter;

    ArrayList<Uri> galleryUriList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importer_asset);
        assetImpoerter = (FrameLayout)findViewById(R.id.layout_asset_importer_frame);

        RecyclerView recyclerView = findViewById(R.id.recyler_asset_impoter_gallery_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        galleryUriList = getVideo();
        assetImporterAdapter = new AssetImporterAdapter(galleryUriList);
        recyclerView.setAdapter(assetImporterAdapter);
        selectedImg = (ImageView)findViewById(R.id.iv_asset_importer_selectedImg) ;
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
                selectedImg.setImageURI(galleryUriList.get(position));
                selectedImg.setVisibility(View.VISIBLE);
                selectedImg.bringToFront();

            }
        });
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
            gelleryUriList.add(Uri.parse(cursor.getString(2)));
            count++;
        }
        cursor.close();
        return gelleryUriList;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode ==1){
            if(resultCode==RESULT_OK){
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    selectedImg.setImageBitmap(img);
                    selectedImg.setVisibility(View.VISIBLE);
                    selectedImg.bringToFront();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
