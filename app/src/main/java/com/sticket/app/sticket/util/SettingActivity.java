package com.sticket.app.sticket.util;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sticket.app.sticket.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void btnToCamera(View v) {
        onBackPressed();
    }

    public void btnToAccountManagement(View view) {
        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivity(intent);
    }
}
