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

//        Toolbar tb = (Toolbar) findViewById(R.id.app_toolbar) ;
//        setSupportActionBar(tb) ;
//        ActionBar ab = getSupportActionBar() ;
//        ab.show() ;     // 앱바(App Bar) 보이기.
//        ActionBar ab = getSupportActionBar() ;
//
//        ab.setIcon(R.drawable.btn_switch) ;
//        ab.setDisplayUseLogoEnabled(true) ;
//        ab.setDisplayShowHomeEnabled(true) ;
//
//        ab.show();
    }

    public void btnToCamera(View v) {
        onBackPressed();
    }

    public void btnToAccountManagement(View view) {
        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivity(intent);
    }
}
