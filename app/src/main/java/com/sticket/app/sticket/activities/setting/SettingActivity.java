package com.sticket.app.sticket.activities.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sticket.app.sticket.R;
import com.sticket.app.sticket.util.FileUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

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
        //TODO: 폴더 선택 다이얼로그
    }

    public void btnToCamera(View v) {
        onBackPressed();
    }

    public void btnToAccountManagement(View view) {
        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivity(intent);
    }
}
