package com.sticket.app.sticket.activities.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.activities.sign.SigninActivity;
import com.sticket.app.sticket.retrofit.client.ApiClient;
import com.sticket.app.sticket.util.FileUtil;
import com.sticket.app.sticket.util.Preference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.folderpicker.FolderPicker;

public class SettingActivity extends AppCompatActivity {
    private static final int FOLDERPICKER_CODE = 123;

    @BindView(R.id.txtSettingPath)
    TextView pathTxt;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        initViews();
    }

    private void initViews(){
        pathTxt.setText(FileUtil.ALBUM_DIRECTORY_PATH);
    }

    @OnClick(R.id.layoutSavePath)
    void savePathLayoutClick(View view){
        Intent intent = new Intent(this, FolderPicker.class);
        startActivityForResult(intent, FOLDERPICKER_CODE);
    }

    public void btnToCamera(View v) {
        onBackPressed();
    }

    public void btnToAccountManagement(View view) {
        if(ApiClient.getInstance().isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
            //To begin from a selected folder instead of sd card's root folder. Example : Pictures directory
            intent.putExtra("location", FileUtil.ALBUM_DIRECTORY_PATH);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FOLDERPICKER_CODE && resultCode == Activity.RESULT_OK) {

            String directoryLocation = intent.getExtras().getString("data");
            Preference.getInstance().putString(Preference.PREFERENCE_NAME_SAVE_LOCATION, directoryLocation);
            pathTxt.setText(directoryLocation);

            FileUtil.structDirectories();

            Log.i( "folderLocation", directoryLocation );

        }
    }
}
