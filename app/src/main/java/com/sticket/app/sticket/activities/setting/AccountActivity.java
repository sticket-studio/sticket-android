package com.sticket.app.sticket.activities.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sticket.app.sticket.R;

public class AccountActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    public void btnToBack(View view) {
        onBackPressed();
    }
}
