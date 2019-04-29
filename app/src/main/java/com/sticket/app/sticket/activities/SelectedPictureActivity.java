package com.sticket.app.sticket.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.sticket.app.sticket.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SelectedPictureActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_FOR_GALLERY = 1;
    public static final String SELECTED_IMAGE_NAME = "selectedImgName";

    private ImageView selectedPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_picture);

        selectedPicture = findViewById(R.id.imgSelectedPicture);

        Uri data = getIntent().getParcelableExtra(SELECTED_IMAGE_NAME);

        try(InputStream in = getContentResolver().openInputStream(data)) {
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            selectedPicture.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnToBack(View v){
        onBackPressed();
    }
}
